package com.aisino.web.util;

public class IbatisConstants {
	public static final String KEY_QUERY_DZFPXX = "queryDzffxx";
	public static String KEY_QUERY_ROUTS = "queryRouts";
	public static String KEY_QUERY_ROUTS_All = "queryRouts_all";
	public static String KEY_GETUSER_BY_ACCOUNT = "getUserByAccount";
	public static String KEY_GETUSER_BY_ACCOUNT_SAVE = "getSaveUserByAccount";
	public static String KEY_GETMENUS_BY_USERID = "getMenusByUserId";
	public static String KEY_GETROLES_BY_USERID = "getRolesByUserId";
	public static String KEY_UNHOLDROLES_BY_USERID = "getUnholdRolesByUserId";
	public static String KEY_USER_ROLE_DELETE_BY_USERID = "deleteUserRoleByUserId";
	public static String KEY_USER_ROLE_INSERT = "insertUserRole";


	public static String KEY_GETUSER_BY_ID = "getUserById";
	public static String KEY_USER_UPDATE = "updateUser";
	public static String KEY_USER_INSERT = "insertUser";
	public static String KEY_USER_DISABLED = "disableUser";
	public static String KEY_USER_LOGIN = "loginUser";
	public static String KEY_USERLIST_FOR_PAGE = "queryUsersForPage";
	public static String KEY_ROLELIST_FOR_PAGE = "queryRolesForPage";
	public static String KEY_GETROLE_BY_ID = "getRoleById";
	public static String KEY_GETFPSL_BY_ID = "getFpslById";
	public static String KEY_GETMENUS_BY_ROLEID = "getMenusByRoleId";
	public static String KEY_GETMENUS_ALL = "getMenusAll";
	public static String KEY_ROLE_UPDATE = "updateRole";
	public static String KEY_ROLE_INSERT = "insertRole";
	public static String KEY_ROLE_DELETE = "deleteRole";
	public static String KEY_ROLE_MENU_DELETE = "deleteRoleMenu";
	public static String KEY_USER_ROLE_DELETE = "deleteUserRole";
	public static String KEY_ROLE_MENU_INSERT = "insertRoleMenu";

	public static String KBMB_INSERT = "insertKbfpmb";
	public static final String KEY_DELETE_FPMB = "deletefpmbxx";
	public static String KEY_GET_SJC = "getYxbz";
	public static String KEY_UPDATE_SJC = "updateYxbz";
	public static String KEY_INSERT_SJCXX = "insertSjcxx";
	public static String KEY_GET_DZXX = "getDzsj";
	public static String KEY_UPDATE_DZSJ = "updateDzsj";
	public static String KEY_INSERT_DZLSH = "insertDzlsh";
	public static String KEY_UPDATE_DZLSH = "updateDzlsh";
	public static String KEY_FPSL_DELETE = "deleteFpsl";//如果发票申领失败，删除以前记录
	public static String KDY_GET_DZJG = "getDzjg";
	public static String KEY_DZJGXX_FOR_PAGE = "queryDzjgxxForPage";
	public static String KEY_GET_HZDZXX = "queryHzdzxx";
	public static String KEY_HZDZXX_FOR_PAGE = "queryHzdzxxForPage";
	public static String KEY_INSERTFPRGHZDZ = "insertFpRghzdz";
	public static String KEY_CKMXXX_FOR_PAGE = "queryCkmxxxForPage";
	/**
	 * 签章查询
	 */
	public static String KEY_QZCXXX_VIEWINVOICE = "queryQzxx";
	public static String KEY_QZCXXX_CHECK = "checkQzxx";
	public static String KEY_QZCXXX_INSERTNSRSBH = "insertNsrsbh";

	public static String KEY_QZCXXX_UPDATE = "updateQzxx";
	public static String KEY_QZCXXX_INSERT = "insertQzxx";
	public static String KEY_QZCXXX_FOR_PAGE = "queryQzcxxxForPage";
	public static String KEY_HZGN_FOR_PAGE = "queryHzgnForPage";
	public static String KEY_QUERYFPHZXX_LIST = "queryFphzxx";
	public static String KEY_FPCLSB_FOR_PAGE = "queryFpClSbForPage";
	public static String KEY_DZJGXX_VIEWINVOICE = "queryInvoice";
	public static String KEY_DZJGXX_VIEWINVOICEDETAIL = "queryInvoiceDetail";
	public static String KEY_DZJGXX_VIEWDDXX = "queryDdxxDetail";
	public static String KEY_QUERY_FPKJID = "queryFpkjId";
	/**
	 * 查询交易流水信息
	 */
	public static String KEY_JYLSCX_FOR_NSRSBH = "queryNsrsbhByDzdah";

	/**
	 * 查询系统路由列表
	 */
	public static String KEY_QUERY_ROUTE_LIST = "queryRouteList";
	public static String KEY_NSRJC_BY_NSRSBH = "key_nsrjc_by_nsrsbh";   /*查纳税人结存*/
	public static String KEY_NSRJCLS_BY_NSRSBH = "key_nsrjcls_by_nsrsbh";   /*查纳税人结存*/
	public static String KEY_NSRJCLS_BY_FPQHZH_DM_ZLDM = "key_nsrjcls_by_fpqhzh_dm_zldm";   /*根据纳税人识别号，发票种类代码，发票代码查纳税人结存*/
	public static String KEY_QUERY_DSPTBM_BY_SWJGDM = "key_query_dsptbm_by_swjgdm"; /*由税务机关代码查dsptbm*/
	public static String KEY_QUERY_LRRQ_Q_BY_NSRSBH = "kry_query_lrrq_q_by_nsrsbh";	/*查录入时间起  由纳税人识别号  查结存最大时间*/
	public static String KEY_YANZ_PYXZ_BY_PYXZ = "key_yanz_pyxz_by_pyxz";	/*验证票源下载*/
	public static String KEY_YANZ_PYXZ_BY_XZ = "key_yanz_pyxz_by_xz";	/*票源下载校验从czlx=2*/
	public static String KEY_YZNZ_PYXZ_BY_TH = "key_yanz_pyxz_by_th";	/*票员下载校验czlx=0*/
	public static String KEY_INSERT_PYXZ = "key_insert_pyxz"; /*保存票源下载信息*/
	public static String KEY_INSERT_PYXZ_TO_JCLS = "key_insert_pyxz_to_jcls";	/*保存一条结存历史信息*/
	public static String KEY_INSERT_PYXZ_TO_JCYC = "key_insert_pyxz_to_jcyc";	/*保存一条结存历史信息*/
	public static String KEY_QUERY_NSRJCXX_BY_SBHSWJG = "key_query_nsrjcxx_by_sbhswjg";	/*查询纳税人结存信息用于缴销*/
	@Deprecated
	public static String KEY_DELETE_NSRJCXX = "key_delete_nsrjcxx";	/*缴销结存*/
	public static String KEY_ROLLBACK_NSRJCXXBYNSRSBH = "key_rollback_nsrjcxxByNsrsbh";	/*回滚缴销操作*/
	public static String KEY_DELETE_NSRJCXX_NSRSBH = "key_delete_nsrjcxxByNsrsbh";	/*缴销结存*/
	public static String KEY_UPDATE_NSRJCXX_NSRSBH = "key_update_nsrjcxxByNsrsbh";  /*更新纳税人缴销状态*/
	public static String KEY_INSERT_FPJX_TO_JCLS = "key_insert_fpjx_to_jcls";	/*发票缴销保存结存历史记录*/
	public static String KEY_QUERY_NSRDZDAH_BY_NSRSBH = "key_query_nsrdzdah_by_nsrsbh";	/*纳税人识别号查纳税人电子档案号*/
	public static String KEY_QUERY_NSRSBH_BY_NSRDZDAH = "key_query_nsrsbh_by_nsrdzdah";   /*纳税人电子档案号查纳税人信息*/
	public static String KEY_GETFPNSRJCS = "getFpNsrjcList";
	public static String KEY_GETFPNSRJC_BY_QH_ZH = "getFpNsrjcByQhZh";
	public static String KEY_DELFPNSRJC = "delFpnsrjc";
	public static String KEY_UPDATEFPNSRJC_QH = "updateFpnsrjcQh";
	public static String KEY_UPDATEFPNSRJC_ZH = "updFpnsrjc";
	public static String KEY_INSERTFPNSRJC = "insertFpnsrjc";
	public static String KEY_INSERTFPNSRJCLS = "insertFpnsrjcls";
	public static String KEY_FPMBLIST_FOR_PAGE = "queryFpmbsForPage";
	public static String KEY_ROUTEXX_BY_NSRSBH = "getRoutexxByNsrsbh";
	public static String KEY_GET_NSRXXCOUNT_BY_NSRSBH = "getNsrxxCountByNsrsbh";
	public static String KEY_GETFPZL = "getFpzl";
	/**
	 * 查询电商企业信息
	 */
	public static String KEY_NSRZT = "checkNsrzt";
	public static String KEY_DSQYPZLIST_FOR_LIST = "queryDsQYPZForList";
	public static String KEY_FPZDSL_FOR_LIST = "queryFpZdslForList";//查fp_zdsl表
	public static String KEY_FPZDSLZT_FOR_LIST = "queryfpzdslzt";//发票自动申领定时器扫表zdslzt为1的 开启自动申领
	public static String KEY_DSQYXXOBJ_FOR_OBJ = "queryDsQYxxForObj";
	public static String KEY_DSPTXXBYDSPTBM = "queryDsptxxByDsptbm";
	public static String KEY_DSQYXXLIST_FOR_PAGE = "queryDsxxForPage";
	public static String KEY_DSZSXXLIST_FOR_PAGE = "queryZsxxForPage";//电商证书信息
	public static String KEY_DSZSXXLIST_FOR_LIST = "queryZsxxForList";//电商证书信息
	public static String KEY_UPDATEDSZSXX = "updatedszsxx";//更新电商证书信息
	public static String KEY_DELETEDSZSXX = "deletedszsxx";//删除电商证书信息
	public static String KEY_INSERTDSZSXX = "insertdszsxx";//导入证书
	public static String KEY_DSPTXX_FOR_OBJ = "queryDsptxxForQyObj";
	public static String KEY_DSPTXXKT_FOR_OBJ = "queryDsptxxktForQyObj";
	public static String KEY_INSERTDSQYXX = "saveDjNsrxx";
	public static String KEY_INSERTFPZDSL = "saveFPZdsl";//首次获取电商企业信息时入库自动申领状态
	public static String KEY_UPDATEFPZDSL = "updateFPZdsl";//更新自动申领状态
	public static String KEY_INSERTDFPPZ = "saveFppz";
	public static String KEY_UPDATEDSQYXX = "updateQyNsrxx";
	public static String KEY_UPDATEDQYFPPZ = "updateQyFpPz";
	public static final String KEY_UPDATENSRZT = "updateNsrztStatus"; //更新纳税人状态及开票状态

	public static String KEY_DELETEDSQYXX = "deleteDsqyxx";
	public static String KEY_DELETEQYPZXX = "deleteDqypzxx";
	/**
	 * 查询电商平台信息
	 */

	public static String KEY_UPDATEDJDZSWPT = "updateDjDzswpt";
	public static String KEY_DSPTXXLIST_FOR_PAGE = "queryDsptxxForPage";
	public static String KEY_DSPTXXKTLIST_FOR_PAGE = "queryDsptxxktForPage";
	public static String KEY_DSPTXXKT_FOR_LIST = "queryDsptxxForList";
	public static String KEY_DSPTXXKTKTT = "queryDsptxxktt";
	public static String KEY_ROUTELIST_FOR_PAGE = "queryRoutesForPage";
	public static String KEY_GETROUTE_BY_ID = "getRouteById";
	public static String KEY_GETROUTE_BY_SWJG_DM = "getRouteBySwjg_dm";
	public static String KEY_ROUTE_UPDATE = "updateRoute";
	public static String KEY_ROUTE_INSERT = "insertRoute";
	public static String KEY_SAVEJDZSWPT = "saveDjDzswpt";
	public static String KEY_SAVEJDZSWPTKZ = "saveDjDzswptkz";
	public static String KEY_DELETEJDZSWPT = "deleteDjDzswpt";
	public static String KEY_DELETEJDZSWPTKZ = "deleteDjDzswptkz";
	public static String KEY_FPSBCL_UPDATE = "updateKjZt";
	public static String KEY_QUERY_NSRSL = "key_query_nsrsl";
	public static String KEY_QUERY_NSRS = "key_query_nsrs";
	public static String KEY_FPSL_UPDATE = "updateFpsl";
	public static String KEY_FPSL_UPDATE_SLDZT = "updateFpslZt";
	public static String KEY_FPSL_INSERT = "insertFpsl";
	public static String KEY_FPSLLIST_FOR_PAGE = "queryFpslForPage";
	public static String KEY_FPSLLIST_FOR_LIST = "queryFpslForList";

	public static String KEY_QUERYZCMBYDSPTBM = "queryzcmByDsptbm";
	public static String KEY_QUERYFPERRORCOUNT = "queryfperrorcount";


	/**
	 * 从税局同步信息
	 */
	/**
	 * 新增同步记录
	 */
	public static final String KEY_SYNC_INSERTSYNCREQUESTLOG = "insertSyncRequestLog";
	/**
	 * 查询未同步完成的记录
	 */
	public static final String KEY_SYNC_QUERYSYNCREQUESTLOGLIMIT = "querySyncRequestLogLimit";
	/**
	 * 查询未同步完成的记录数
	 */
	public static final String KEY_SYNC_GETSYNCREQUESTCOUNT = "getSyncRequestCount";

	/**
	 * 检查同步记录是否已经入库
	 */
	public static final String KEY_SYNC_CHECKSYNCREQUESTEXIST = "checkSyncRequestExist";
	/**
	 * 更新同步状态
	 */
	public static final String KEY_SYNC_UPDATESYNCREQUESTSTATUS = "updateSyncRequestStatus";
	/**
	 * 更新汇总对账结果
	 */
	public static final String KEY_UPDATE_HZDZJG = "key_update_hzdzjg";

	public static final String KEY_UPDATE_MXDZ_HZDZJG = "key_update_mxdz_hzdzjg";

	public static final String KEY_UPDATE_FPKJ_HZDZ_FALG = "modifyFpkjHzdzFlagSql";
	/**
	 * 修改fp_kj_bf备份表
	 */
	public static final String KEY_UPDATE_FPKJ_HZDZ_FALG_BF = "modifyFpkjHzdzFlagSqlBf";

	public static final String KEY_UPDATE_MXDZJG = "key_update_mxdzjg";

	public static final String KEY_UPDATE_HZDZBZ = "updateHzdzJg";

	public static final String KEY_UPDATE_MXDZZBJG = "key_update_mxdzjgzb";

	public static final String KEY_QUERYHZDZ_FOR_OBJ = "queryHzdzForObj";
	public static final String KEY_QUERYRGHZDZ_FOR_OBJ = "queryRghzdzForObj";
	public static final String KEY_QUERYDZLSH = "querydzlsh";
	public static final String KEY_UPDATE_DZLSH_VAL = "update_dzlsh_val";

	public static final String KEY_QUERYROUTEURLBYID = "queryRouteUrlById";
	/**
	 * 插入发票对帐
	 */
	public static final String KEY_INSERTFPHZDZ = "insertFphzdz";
	/**
	 * 插入明细对帐
	 */
	public static final String KEY_INSERTFPMXDZ = "insertFpmxdz";
	/**
	 * 插入明细对帐子表
	 */
	public static final String KEY_INSERTFPMXDZZB = "insertFpmxdzzb";
	/**
	 * 删除明细对账表数据
	 */
	public static final String KEY_DELETEFPMXDZ = "deletefpmxdz";
	/**
	 * 删除明细对账表子表数据
	 */
	public static final String KEY_DELETEFPMXDZZB = "deletefpmxdzzb";
	/**
	 * 查询失败的汇总发票对帐列表
	 */
	public static final String KEY_QUERYHZDZSB_FOR_LIST = "queryHzdzSbForList";
	/**
	 * 查询失败的汇总发票对帐列表
	 */
	public static final String KEY_QUERYMXDZFPXX_FOR_LIST = "queryMxdzFpxxForList";
	/**
	 * 查询开具明细信息
	 */
	public static final String KEY_QUERYMXFORFPSC = "queryKjmxForFpsc";
	/**
	 * 查询开具物流信息
	 */
	public static final String KEY_QUERYWLXXFORFPSC = "queryWlxxForFpsc";
	/**
	 * 查询开具支付信息
	 */
	public static final String KEY_QUERYZFXXFORFPSC = "queryZfxxForFpsc";
	/**
	 * 查询开具id
	 */
	public static final String KEY_QUERYFPKJIDBYKPLSH = "queryfpkjidbykplsh";
	/**
	 * 查询开具订单信息
	 */
	public static final String KEY_QUERYDDXXFORFPSC = "queryDdxxForFpsc";
	/**
	 * 上传失败 修改发票信息
	 */
	public static final String KEY_UPDATEFPKJJGXX = "updatefpkjjgxx";
	/**上传成功 修改发票信息*/
	/**
	 * 记录上传流水号信息
	 */
	public static final String KEY_UPDATEFPKJSUCCESS = "updatefpkjsuccess";
	public static final String KEY_INSERTFPSCLSH = "insertFpsclsh";
	/**
	 * 保存发票与流水号对应关系表
	 */
	public static final String KEY_INSERTFPKJ_FPSCLSH = "insertFpkj_fpsclsh";
	/**
	 * 查询上传流水号信息
	 */
	public static final String KEY_QUERYFPSCLSH = "queryFpsclsh";
	/*获取上传结果*/
	public static final String KEY_QUERYFPSCJGLIST = "queryFpscJgList";
	/**
	 * 根据上传流水号查询发票信息
	 */
	public static final String KEY_QUERYFPXXBYSCLSH = "queryFpxxBysclsh";
	/**
	 * 修改上传流水号信息
	 */
	public static final String KEY_UPDATEFPSCLSH = "updateFpsclshzt";
	/**
	 * 根据发票代码号码查询发票id
	 */
	public static final String KEY_QUERYFPKJIDBYDMHM = "queryfpkjidBydmhm";
	/**
	 * 根据发票上传流水号查询发票id
	 */
	public static final String KEY_QUERYFPKJIDBYFPSCLSH = "queryfpkjidbyfpsclsh";
	/**
	 * 发票申领取结果
	 */
	public static String KEY_FPSLJG_FOR_LIST = "queryFpslForListTask";
	/**
	 * 查询fpsl表
	 */
	public static String KEY_FPSLJG_FOR_DSPTBM = "queryFpslFordsptbm";
	/**
	 * 修改上传流水号信息
	 */
	public static String KEY_FPSCJG_FAIL = "update_fpsclshBySclsh";
	
	 /**
     * 根据id更新log发票上传51失败状态
     */
    public static final String KEY_UPDATEFAILFORFPSC = "updatefailforfpsc";

	public static String KEY_FPKJSCJG_FAIL = "update_fpkjsclshBySclsh";

	public static String KEY_QUERY_FPKJSCLSH = "query_fpkjsclshBySclsh";

	public static String KEY_UPDATE_KJLOG_KPZT = "update_fpkjlogByid";

	public static String UPDATE_INVUPLOADRESULTFORPLATFORM = "update_invUploadResultForPlatform";

	public static String UPDATE_INVUPLOADRESULTFOR51 = "update_invUploadResultFor51";
	
	public static String KEY_UPDATE_FPKJLOG = "key_update_fpkjlog";

	public static String KEY_UPDATE_FPKJLSH = "key_update_fpkjlsh";

	public static String KEY_QUERY_FPMXDZ = "query_fpmxdz";
	/**
	 * 修改发票的对账标志
	 */
	public static String KEY_UPDATE_MXDZFPKJDZZT = "update_mxdzfpkjdzzt";

	/**
	 * 修改发票备份表的对账标志
	 */
	public static String KEY_UPDATE_MXDZFPKJDZZT_BF = "update_mxdzfpkjdzzt_bf";
	/**
	 * 修改发票的对账标志
	 */
	public static String KEY_UPDATE_MXDZFPKJDZZTBYDZLSH = "update_mxdzfpkjdzztbydzlsh";
	/**
	 * 根据对账流水号查询发票代码发票号码
	 */
	public static String KEY_UPDATE_FP_DM_FM_HMBYDZLSH = "key_update_fp_dm_fm_hmbydzlsh";
	/**
	 * 根据发票代码 发票号码 ，开票日期 查询发票状态信息
	 */
	public static String kEY_QUERY_FPZTXXBYPARAM = "query_fpztxxbyparam";
	
	public static String KEY_UPDATE_FPKJZT = "update_fpkjzt";
	/**
	 * 根据id查询发票pdf路径
	 */
	public static String kEY_QUERY_PDFPATHBYID = "query_pdfpathbyid";
	/**
	 * 发票申领时 根据nsrsbh查询fa_nsrjc表 获取发票份数总和
	 */
	public static String KEY_QUERY_TOTALFS = "query_totalfs";
	/**
	 * 根据nsrsbh和fpzl_dm查询fp_pz表 用来获取企业最高持票量
	 */
	public static String KEY_QUERY_QYZGCPL = "query_qyzgcpl";
	/**
	 * 根据nsrsbh 查询  fp_fpsl 表  获取申领中的份数
	 */
	public static String KEY_QUERY_SLZFS = "query_slzfs";
}
