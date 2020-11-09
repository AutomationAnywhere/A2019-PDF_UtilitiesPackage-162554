
package com.automationanywhere.botcommand.sk;



import static com.automationanywhere.commandsdk.model.AttributeType.CREDENTIAL;

import java.util.ArrayList;
import java.util.List;

import com.automationanywhere.commandsdk.annotations.BotCommand;
import com.automationanywhere.commandsdk.annotations.CommandPkg;
import com.automationanywhere.commandsdk.annotations.Idx;
import com.automationanywhere.commandsdk.annotations.Pkg;
import com.automationanywhere.commandsdk.annotations.rules.FileExtension;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.model.AttributeType;
import com.automationanywhere.commandsdk.model.DataType;
import com.automationanywhere.core.security.SecureString;
import com.automationanywhere.commandsdk.annotations.Execute;


/**
 * @author Stefan Karsten
 *
 */

@BotCommand
@CommandPkg(label = "Merge PDF files", name = "mergepdf",
        description = "Merge two PDF files into one",
        node_label = "Merge PDF",  icon = "pkg.svg")

public class MergePDF {
	   
	@Execute
    public void action(@Idx(index = "1", type = AttributeType.FILE)  @Pkg(label = "1. Source File"  , default_value_type = DataType.FILE ) @FileExtension("pdf") @NotEmpty String file1,
    							@Idx(index = "2", type = CREDENTIAL) @Pkg(label = "Password for 1.") SecureString password1,
    							@Idx(index = "3", type = AttributeType.FILE)  @Pkg(label = "2. Source File"  , default_value_type = DataType.FILE ) @FileExtension("pdf") @NotEmpty String file2,
    		                    @Idx(index = "4", type = CREDENTIAL) @Pkg(label =  "Password for 2.") SecureString password2,
    							@Idx(index = "5", type = AttributeType.TEXT)  @Pkg(label = "Resulting File"  , default_value_type = DataType.STRING)  @NotEmpty String outputfile				
    				) throws Exception
     {

		 List<String> list = new ArrayList<String>();
		 List<String> passwords = new ArrayList<String>();
     	 PDFUtils pdfutil = new PDFUtils();
		 
	     list.add(file1);
	     list.add(file2);
		 
	     passwords.add(password1.getInsecureString());
	     passwords.add(password2.getInsecureString());

	     pdfutil.doMerge(list,passwords, outputfile);
	  }
		

}

