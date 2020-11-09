
package com.automationanywhere.botcommand.sk;



import static com.automationanywhere.commandsdk.model.AttributeType.CREDENTIAL;


import com.automationanywhere.botcommand.data.impl.StringValue;
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
@CommandPkg(label = "Get Full Text", name = "gettext",
        description = "Get Full Text",
        node_label = "Get Full Text",  icon = "pkg.svg",
        return_type=DataType.STRING, return_label="Text", return_required=true)

public class GetFullText {
	   
	@Execute
    public StringValue action(@Idx(index = "1", type = AttributeType.FILE)  @Pkg(label = "Adobe File"  , default_value_type = DataType.FILE ) @FileExtension("pdf") @NotEmpty String file,
    						  @Idx(index = "2", type = CREDENTIAL) @Pkg(label = "Password") SecureString password	
    				) throws Exception
     {
		PDFUtils pdfutil = new PDFUtils();
		String text= pdfutil.getText(file);
	     
	     return new StringValue(text);
	  }
		

}

