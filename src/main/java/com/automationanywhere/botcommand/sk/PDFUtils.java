package com.automationanywhere.botcommand.sk;



import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.w3c.dom.Node;
import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.data.impl.StringValue;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.XfaForm;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.itextpdf.tool.xml.XMLWorkerHelper;


public class PDFUtils {
	
	
	
	 private static final Logger logger = LogManager.getLogger(PDFUtils.class);
	 
	 
	 public  void doMerge(List<String> list, List<String> passwords , String out) throws Exception {
		 
	        Document document = new Document();
	        PdfReader reader ;
	        PdfCopy copy = new PdfCopy(document, new FileOutputStream(out));
	        copy.setMergeFields();
	        document.open();


	            
	        for (int i = 0; i < list.size(); i++) {
	        	String in = list.get(i);
	        	String password = (passwords.get(i) == null) ? "" : passwords.get(i) ;
				if (!password.equals("")) {
					reader = new PdfReader(new FileInputStream(new File(in)),password.getBytes());
				}
				else {
					reader = new PdfReader(new FileInputStream(new File(in)));
				}
				copy.addDocument(reader);
			//	reader.close();
	            
	        }
	        
	        copy.flush();
	        document.close();
	        copy.close();
	    }
	 
	 
	 
	 public  Map<String,Value> readForm(InputStream in, String password) throws Exception {
		 
			Map<String,Value> map = new HashMap<String,Value>();
			 
	     	PdfReader reader ;
		    password = (password == null) ? "" : password;
			if (!password.equals("")) {
				reader = new PdfReader(in,password.getBytes());
			}
			else {
				reader = new PdfReader(in);
			}

			AcroFields fields = reader.getAcroFields();

			Set<String> fldNames = fields.getFields().keySet();

			for (String fldName : fldNames) {	
					map.put(fldName, new StringValue(fields.getField( fldName ).toString()));
			}
			return map;
		 }
		 
	 
	
	 
	 public  void setForm(String in, String password, String out,  Map<String,Value>fields,Boolean isXFA, Boolean flatten) throws Exception {
		
			
				
				PdfReader reader ;
				password = (password == null) ? "" : password ;
				if (!password.equals("")) {
					reader = new PdfReader(in, password.getBytes());
				}
				else {
					reader = new PdfReader(in);
				}

				boolean append = (flatten) ? false : true;

				if (isXFA) {
					
			        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(out) ,'\0',append);
			        AcroFields form = stamper.getAcroFields();
			        XfaForm xfa = form.getXfa();
			        for (Entry field : fields.entrySet()) {
				        String key = field.getKey().toString();
			        	key = key.replaceAll("\\.#subform\\[0\\]","");
			        	String somName = xfa.findDatasetsName(key);
			        	Node xfaNode = xfa.findDatasetsNode(somName);
			        	xfaNode.setTextContent(field.getValue().toString());
			        	xfa.setChanged(true);
			        }
			    	xfa.setXfa(stamper.getWriter());
			    	stamper.setFormFlattening(flatten); 
			    	stamper.close();
			    	reader.close();
					
				}
				else
				{
				
				   
			        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(out) ,'\0',append);
			        AcroFields form = stamper.getAcroFields();
			        for (Entry field : fields.entrySet()) {
				        String key = field.getKey().toString();
				        form.setField(key, field.getValue().toString());

			        }
			        stamper.setFormFlattening(flatten); 
			    	stamper.close();
			    	reader.close();
		    	 
				}
				
					
			
	 }
	 
	 
	 
	 public String getText(String in) throws Exception {
	      PdfReader reader = new PdfReader(in);
	      String txt = "" ;
	      PdfReaderContentParser parser = new PdfReaderContentParser(reader);
	      TextExtractionStrategy strategy;
	      for (int i = 1; i <= reader.getNumberOfPages(); i++) {
	    	strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
            txt = txt + strategy.getResultantText();
	      }
	      reader.close();
	      return txt;
	    }
	 


			
		     
		 

	public  String getFileExtension(File file) {
	        String extension = "";
	 
	        try {
	            if (file != null && file.exists()) {
	                String name = file.getName();
	                extension = name.substring(name.lastIndexOf("."));
	            }
	        } catch (Exception e) {
	            extension = "";
	        }
	        
	        return extension;
	 }
	 
	public  String HTMLtoPdf(String htmlfile,String pdffile, String orientation, String sizename) {
        String result = "Success";
        try {

        	
            org.jsoup.nodes.Document documentJ = Jsoup.parse( new File( htmlfile ) , "utf-8" );
            documentJ.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);  
            ByteArrayInputStream xmlStream = new ByteArrayInputStream( documentJ.html().getBytes());
            Document document = new Document();
            Rectangle pagesize = (Rectangle) ((orientation.contentEquals("Landscape")) ? PageSize.getRectangle(sizename).rotate() : PageSize.getRectangle(sizename));
            document.setPageSize(pagesize);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdffile));
            document.open();
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, xmlStream,Charset.forName("UTF8"));
            // step 5
            document.close();
 
        
        return result;
        }
        catch (Exception e) {
            result = e.getMessage();
        }
        
	 
        return result;
	}
	 
}
