
package com.automationanywhere.botcommand.sk;



import static com.automationanywhere.commandsdk.model.AttributeType.CREDENTIAL;


import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.data.impl.DictionaryValue;
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
@CommandPkg(label = "Read Form Fields", name = "readform",
        description = "Read Form Fields",
        node_label = "Read Form Fields",  icon = "pkg.svg",
        return_type=DataType.DICTIONARY, return_sub_type=DataType.STRING ,return_label="Form Fields", return_required=true)

public class ReadFormFields {
	   
	@Execute
    public DictionaryValue action(@Idx(index = "1", type = AttributeType.FILE)  @Pkg(label = "Adobe Form File"  , default_value_type = DataType.FILE ) @FileExtension("pdf") @NotEmpty String file,
    							@Idx(index = "2", type = CREDENTIAL) @Pkg(label = "Password") SecureString password	
    				) throws Exception
     {

	     FileInputStream in = new FileInputStream(new File(file));
	     String unsecurepassword = (password != null) ? password.getInsecureString() : "";
     	 PDFUtils pdfutil = new PDFUtils();
	  

	     Map<String, Value> map = pdfutil.readForm(in, unsecurepassword);
	     
	     DictionaryValue dict = new DictionaryValue();
	     dict.set(map);
	     return dict;
	  }
		

}

