
import com.co.autentic.RTDDataSearch.common.utils.GenUtils;
import com.co.autentic.RTDDataSearch.importData.aws.AWSS3Connection;
import com.co.autentic.RTDDataSearch.importData.aws.models.AWSConfig;
import com.co.autentic.RTDDataSearch.importData.aws.models.TransactionItem;
import com.co.autentic.RTDDataSearch.importData.services.getDataService;

import java.io.IOException;
import java.text.ParseException;
import java.util.Properties;

public class Test {
    private static Properties envConfig;
    private static AWSS3Connection awss3Connection;
    public static void main(String[] args) throws IOException, ParseException {

        getDataService get = new getDataService();
        get.getFileS3();
        TransactionItem ti = get.getData("CC1018510706", "10");
        if(ti != null){
            System.out.println(ti.getEntity());
        }
    }

}
