package com.aisino.domain.einvoice;

import com.aisino.domain.BaseService;
import com.aisino.domain.einvoice.entity.InvoicePDFEntity;

/**
 * Created by Martin.Ou on 2014/9/15.
 * <p/>
 * 生成PDF文件服务接口
 *
 * @see com.aisino.domain.BaseService
 */
public interface InvoicePDFProducerService extends BaseService {

    /**
     * 产生发票PDF文件，并返回文件名
     *
     * @param pdfEntity 生成PDF文件需要的数据项
     * @return 返回文件名
     * @throws Exception
     */
    String generatePdfFile(InvoicePDFEntity pdfEntity) throws Exception;
}
