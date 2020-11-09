
package com.automationanywhere.botcommand.sk;




import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
@CommandPkg(label = "Merge all PDF files", name = "mergedirpdf",
        description = "Merge all PDF files in a directory into one",
        node_label = "Merge all PDF files", icon = "pkg.svg")

public class MergeDirPDF {
	
	 private static final Logger logger = LogManager.getLogger(MergeDirPDF.class);
		
	   
	@Execute
    public void action(@Idx(index = "1", type = AttributeType.TEXT)  @Pkg(label = "Directory with PDF files"  , default_value_type = DataType.STRING )  @NotEmpty String dir,
    				   @Idx(index = "2", type = AttributeType.TEXT)  @Pkg(label = "Resulting File"  , default_value_type = DataType.STRING)  @NotEmpty String outputfile				
    				) throws Exception
     {

		
		 List<String> list = new ArrayList<String>();
		 List<String> passwords = new ArrayList<String>();
     	 PDFUtils pdfutil = new PDFUtils();
		 
		   File[] files = new File(dir).listFiles();
		    for (File file : files) {
		        if (!file.isDirectory()) {
	        		if ( pdfutil.getFileExtension(file).toLowerCase().equals(".pdf")) {
		        		logger.info("PDF File Name to add"+file.getAbsolutePath());
		        		list.add(file.getAbsolutePath());
		        		passwords.add("");
		        	}
		        }
		    }
		    if (list.size() > 0) {
		    	pdfutil.doMerge(list, passwords, outputfile);
		    }
	}
		

}

