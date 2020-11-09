
package com.automationanywhere.botcommand.sk;



import static com.automationanywhere.commandsdk.model.AttributeType.SELECT;
import static com.automationanywhere.commandsdk.model.DataType.STRING;


import com.automationanywhere.botcommand.data.impl.StringValue;
import com.automationanywhere.commandsdk.annotations.BotCommand;
import com.automationanywhere.commandsdk.annotations.CommandPkg;
import com.automationanywhere.commandsdk.annotations.Idx;
import com.automationanywhere.commandsdk.annotations.Pkg;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.model.AttributeType;
import com.automationanywhere.commandsdk.model.DataType;
import com.automationanywhere.commandsdk.annotations.Execute;


/**
 * @author Stefan Karsten
 *
 */

@BotCommand
@CommandPkg(label = "HTML to PDF", name = "htmltopdf",
        description = "Converts a HTML file to a PDF file",
        node_label = "HTML to PDF",  icon = "pkg.svg",
        return_type=DataType.STRING ,return_label="Status", return_required=false)

public class HTMLtoPDF {
	   
	@Execute
    public StringValue action(@Idx(index = "1", type = AttributeType.FILE)  @Pkg(label = "HTML Input File"  , default_value_type = DataType.FILE) @NotEmpty String htmlfile,
    					      @Idx(index = "2", type = AttributeType.FILE)  @Pkg(label = "PDF Output File"  , default_value_type = DataType.FILE )  @NotEmpty String pdffile,
							  @Idx(index = "3", type = SELECT, options = {
										@Idx.Option(index = "3.1", pkg = @Pkg(label = "A3", value = "A3")),
										@Idx.Option(index = "3.2", pkg = @Pkg(label = "A4", value = "A4")),
										@Idx.Option(index = "3.3", pkg = @Pkg(label = "A5", value = "A5")),
										@Idx.Option(index = "3.4", pkg = @Pkg(label = "Tabloid", value = "Tabloid")),
										@Idx.Option(index = "3.5", pkg = @Pkg(label = "Legal", value = "Legal")),
										@Idx.Option(index = "3.6", pkg = @Pkg(label = "Letter", value = "Letter"))
										}) @Pkg(label = "Orientation", default_value = "A4", default_value_type = STRING) @NotEmpty String pagesize,
							  @Idx(index = "4", type = SELECT, options = {
										@Idx.Option(index = "4.1", pkg = @Pkg(label = "Portrait", value = "Portrait")),
										@Idx.Option(index = "4.2", pkg = @Pkg(label = "Landscape", value = "Landscape"))
										}) @Pkg(label = "Page Size", default_value = "portrait", default_value_type = STRING) @NotEmpty String orientation
    						) throws Exception
     {

	  
		PDFUtils pdfutil = new PDFUtils();
	    String result = pdfutil.HTMLtoPdf(htmlfile,pdffile,orientation,pagesize);
	     

	     return new StringValue(result);
	  }
		

}

