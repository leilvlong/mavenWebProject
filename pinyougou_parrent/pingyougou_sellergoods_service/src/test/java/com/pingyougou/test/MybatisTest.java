package com.pingyougou.test;


import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 描述
 *
 * @author 三国的包子
 * @version 1.0
 * @package com.pinyougou.test *
 * @since 1.0
 */
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
@RunWith(SpringRunner.class)
public class MybatisTest {

    @Test
    public void uploadFastdfs() throws Exception{
        ClientGlobal.init("F:\\project_java\\mavenProject\\pinyougou_parrent\\pingyougou_sellergoods_service\\src\\main\\resources\\fdfs_client.conf");
        TrackerClient trackerClient  = new TrackerClient();

        TrackerServer trackerServer = trackerClient.getConnection();

        StorageClient storageClient = new StorageClient(trackerServer,null);
        String[] jpgs = storageClient.upload_file("C:\\Users\\L1455013965\\Pictures\\Saved Pictures\\1520986107703.jpg", "jpg", null);
        for (String jpg : jpgs) {
            System.out.println(jpg);
        }

    }


    @Test
    public void splieString(){
        String file = "123.456.jpg";

        String[] split = file.split("\\.");
        System.out.println(split.length);
        System.out.println(split[split.length - 1]);

    }
}
