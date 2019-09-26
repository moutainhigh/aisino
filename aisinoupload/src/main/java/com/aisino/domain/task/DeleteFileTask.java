package com.aisino.domain.task;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aisino.domain.einvoice.resultService.IBusinessDataService;
import com.aisino.domain.task.dao.ITaskDao;
import com.aisino.domain.task.service.IFpxxTaskService;
import com.aisino.protocol.bean.REQUEST_FPKJ;
import com.aisino.web.util.Constants;
import com.aisino.web.util.SystemConfig;

@Service
public class DeleteFileTask {

    @Autowired
    protected ITaskDao taskDao;
    @Autowired
    private IFpxxTaskService fpxxTaskService;
    @Autowired
    private IBusinessDataService businessDataService;
    private static Logger logger = LoggerFactory.getLogger(DeleteFileTask.class);

    /**
     * 发票上传定时器
     * 
     * @throws SQLException
     */
    public void deleteFileTaskJob() {
        logger.info("pdf删除任务开始...");
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("fpkj_zt", Constants.INV_UPLOAD_SUCCESS);
            int start = 0;
            int size = Integer.parseInt(Constants.SINGLE_FPSC);
            map.put("start", start);
            map.put("count", size);
            // 获取所有需要删除pdf的发票信息
            List<REQUEST_FPKJ> fpkjList = fpxxTaskService.queryPdf(map);
            for (int i = 1; i <= fpkjList.size(); i++) {
                String path = fpkjList.get(i).getPDF_FILE();
                businessDataService.modifyDeletePdf(map, path);
            }
            businessDataService.modifyDeleteFile(new File(SystemConfig.pyUrl));//获取票源的根目录然后删除

        } catch (Exception e) {
            logger.error("pdf删除任务结束..." + e.getMessage());
        }
        logger.info("pdf删除任务结束...");
    }

}
