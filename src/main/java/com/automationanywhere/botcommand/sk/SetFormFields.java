
package com.automationanywhere.botcommand.sk;



import static com.automationanywhere.commandsdk.model.AttributeType.CREDENTIAL;

import java.util.Map;

import com.automationanywhere.botcommand.data.Value;
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
@CommandPkg(label = "Set Form Fields", name = "setform",
        description = "Set Form Fields",
        node_label = "Set Form Fields",  icon = "pkg.svg")

public class SetFormFields {
	   
	@Execute
    public void  action(@Idx(index = "1", type = AttributeType.FILE)  @Pkg(label = "Input Adobe Form File"  , default_value_type = DataType.FILE ) @FileExtension("pdf") @NotEmpty String filein,
    							  @Idx(index = "2", type = CREDENTIAL) @Pkg(label = "Password") SecureString password ,
    							  @Idx(index = "3", type = AttributeType.FILE)  @Pkg(label = "Output Adobe Form File"  , default_value_type = DataType.FILE ) @FileExtension("pdf") @NotEmpty String fileout,
    							  @Idx(index = "4", type = AttributeType.DICTIONARY) @Pkg(label = "Form Fields" , default_value_type = DataType.DICTIONARY) @NotEmpty Map<String,Value> fields,
    							  @Idx(index = "5", type = AttributeType.BOOLEAN) @Pkg(label = "XFA Form" , default_value_type = DataType.BOOLEAN , default_value = "false") Boolean isXFA,
    							  @Idx(index = "6", type = AttributeType.BOOLEAN) @Pkg(label = "Save non-editable" , default_value_type = DataType.BOOLEAN , default_value = "false") Boolean flatten
    				) throws Exception
     {

	     String unsecurepassword = (password != null) ? password.getInsecureString() : "";
     	 PDFUtils pdfutil = new PDFUtils();
     	 

	     isXFA = (isXFA  == null) ? false : isXFA;
	     flatten= (flatten == null) ? false : flatten;

	     pdfutil.setForm(filein, unsecurepassword, fileout, fields,isXFA,flatten);

	  }
		

}

