
package com.aisino.cxf.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for eiActualBill complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eiActualBill">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="antiFankCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billOperate" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="blueIsn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="businessId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="channelCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="creater" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="eiActualDetailList" type="{http://www.jd.com}eiActualDetail" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fileUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hashTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="initActualId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="invoiceCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="invoiceDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="invoiceModel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="invoiceNo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="invoiceTypeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ivcContentName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ivcContentType" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ivcTitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ivcType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="memo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="oldState" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="operatorCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="operatorName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderFinishTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="orderTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="orgId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="payerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="payerNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="receiverName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="receiverNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sourceId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="taxRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="totalPrice" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="updateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="updater" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="yn" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eiActualBill", propOrder = {
    "antiFankCode",
    "billOperate",
    "blueIsn",
    "businessId",
    "channelCode",
    "createTime",
    "creater",
    "customerId",
    "eiActualDetailList",
    "email",
    "fileUrl",
    "hashTime",
    "id",
    "initActualId",
    "invoiceCode",
    "invoiceDate",
    "invoiceModel",
    "invoiceNo",
    "invoiceTypeCode",
    "ivcContentName",
    "ivcContentType",
    "ivcTitle",
    "ivcType",
    "memo",
    "mobile",
    "oldState",
    "operatorCode",
    "operatorName",
    "orderFinishTime",
    "orderTime",
    "orgId",
    "payerName",
    "payerNo",
    "receiverName",
    "receiverNo",
    "sourceId",
    "state",
    "taxRate",
    "totalPrice",
    "updateTime",
    "updater",
    "yn"
})
public class EiActualBill {

    protected String antiFankCode;
    protected Short billOperate;
    protected String blueIsn;
    protected String businessId;
    protected String channelCode;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createTime;
    protected String creater;
    protected String customerId;
    @XmlElement(nillable = true)
    protected List<EiActualDetail> eiActualDetailList;
    protected String email;
    protected String fileUrl;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar hashTime;
    protected Long id;
    protected Long initActualId;
    protected String invoiceCode;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar invoiceDate;
    protected String invoiceModel;
    protected Integer invoiceNo;
    protected String invoiceTypeCode;
    protected String ivcContentName;
    protected Integer ivcContentType;
    protected String ivcTitle;
    protected String ivcType;
    protected String memo;
    protected String mobile;
    protected Short oldState;
    protected String operatorCode;
    protected String operatorName;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar orderFinishTime;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar orderTime;
    protected Integer orgId;
    protected String payerName;
    protected String payerNo;
    protected String receiverName;
    protected String receiverNo;
    protected Integer sourceId;
    protected Short state;
    protected BigDecimal taxRate;
    protected BigDecimal totalPrice;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar updateTime;
    protected String updater;
    protected Integer yn;

    /**
     * Gets the value of the antiFankCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAntiFankCode() {
        return antiFankCode;
    }

    /**
     * Sets the value of the antiFankCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAntiFankCode(String value) {
        this.antiFankCode = value;
    }

    /**
     * Gets the value of the billOperate property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getBillOperate() {
        return billOperate;
    }

    /**
     * Sets the value of the billOperate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setBillOperate(Short value) {
        this.billOperate = value;
    }

    /**
     * Gets the value of the blueIsn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBlueIsn() {
        return blueIsn;
    }

    /**
     * Sets the value of the blueIsn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBlueIsn(String value) {
        this.blueIsn = value;
    }

    /**
     * Gets the value of the businessId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessId() {
        return businessId;
    }

    /**
     * Sets the value of the businessId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessId(String value) {
        this.businessId = value;
    }

    /**
     * Gets the value of the channelCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannelCode() {
        return channelCode;
    }

    /**
     * Sets the value of the channelCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannelCode(String value) {
        this.channelCode = value;
    }

    /**
     * Gets the value of the createTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreateTime() {
        return createTime;
    }

    /**
     * Sets the value of the createTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreateTime(XMLGregorianCalendar value) {
        this.createTime = value;
    }

    /**
     * Gets the value of the creater property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreater() {
        return creater;
    }

    /**
     * Sets the value of the creater property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreater(String value) {
        this.creater = value;
    }

    /**
     * Gets the value of the customerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Sets the value of the customerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerId(String value) {
        this.customerId = value;
    }

    /**
     * Gets the value of the eiActualDetailList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the eiActualDetailList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEiActualDetailList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EiActualDetail }
     * 
     * 
     */
    public List<EiActualDetail> getEiActualDetailList() {
        if (eiActualDetailList == null) {
            eiActualDetailList = new ArrayList<EiActualDetail>();
        }
        return this.eiActualDetailList;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the fileUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileUrl() {
        return fileUrl;
    }

    /**
     * Sets the value of the fileUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileUrl(String value) {
        this.fileUrl = value;
    }

    /**
     * Gets the value of the hashTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getHashTime() {
        return hashTime;
    }

    /**
     * Sets the value of the hashTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setHashTime(XMLGregorianCalendar value) {
        this.hashTime = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Gets the value of the initActualId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getInitActualId() {
        return initActualId;
    }

    /**
     * Sets the value of the initActualId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setInitActualId(Long value) {
        this.initActualId = value;
    }

    /**
     * Gets the value of the invoiceCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoiceCode() {
        return invoiceCode;
    }

    /**
     * Sets the value of the invoiceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoiceCode(String value) {
        this.invoiceCode = value;
    }

    /**
     * Gets the value of the invoiceDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInvoiceDate() {
        return invoiceDate;
    }

    /**
     * Sets the value of the invoiceDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInvoiceDate(XMLGregorianCalendar value) {
        this.invoiceDate = value;
    }

    /**
     * Gets the value of the invoiceModel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoiceModel() {
        return invoiceModel;
    }

    /**
     * Sets the value of the invoiceModel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoiceModel(String value) {
        this.invoiceModel = value;
    }

    /**
     * Gets the value of the invoiceNo property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * Sets the value of the invoiceNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInvoiceNo(Integer value) {
        this.invoiceNo = value;
    }

    /**
     * Gets the value of the invoiceTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoiceTypeCode() {
        return invoiceTypeCode;
    }

    /**
     * Sets the value of the invoiceTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoiceTypeCode(String value) {
        this.invoiceTypeCode = value;
    }

    /**
     * Gets the value of the ivcContentName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIvcContentName() {
        return ivcContentName;
    }

    /**
     * Sets the value of the ivcContentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIvcContentName(String value) {
        this.ivcContentName = value;
    }

    /**
     * Gets the value of the ivcContentType property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIvcContentType() {
        return ivcContentType;
    }

    /**
     * Sets the value of the ivcContentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIvcContentType(Integer value) {
        this.ivcContentType = value;
    }

    /**
     * Gets the value of the ivcTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIvcTitle() {
        return ivcTitle;
    }

    /**
     * Sets the value of the ivcTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIvcTitle(String value) {
        this.ivcTitle = value;
    }

    /**
     * Gets the value of the ivcType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIvcType() {
        return ivcType;
    }

    /**
     * Sets the value of the ivcType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIvcType(String value) {
        this.ivcType = value;
    }

    /**
     * Gets the value of the memo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMemo() {
        return memo;
    }

    /**
     * Sets the value of the memo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMemo(String value) {
        this.memo = value;
    }

    /**
     * Gets the value of the mobile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Sets the value of the mobile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobile(String value) {
        this.mobile = value;
    }

    /**
     * Gets the value of the oldState property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getOldState() {
        return oldState;
    }

    /**
     * Sets the value of the oldState property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setOldState(Short value) {
        this.oldState = value;
    }

    /**
     * Gets the value of the operatorCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperatorCode() {
        return operatorCode;
    }

    /**
     * Sets the value of the operatorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperatorCode(String value) {
        this.operatorCode = value;
    }

    /**
     * Gets the value of the operatorName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * Sets the value of the operatorName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperatorName(String value) {
        this.operatorName = value;
    }

    /**
     * Gets the value of the orderFinishTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOrderFinishTime() {
        return orderFinishTime;
    }

    /**
     * Sets the value of the orderFinishTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOrderFinishTime(XMLGregorianCalendar value) {
        this.orderFinishTime = value;
    }

    /**
     * Gets the value of the orderTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOrderTime() {
        return orderTime;
    }

    /**
     * Sets the value of the orderTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOrderTime(XMLGregorianCalendar value) {
        this.orderTime = value;
    }

    /**
     * Gets the value of the orgId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOrgId() {
        return orgId;
    }

    /**
     * Sets the value of the orgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOrgId(Integer value) {
        this.orgId = value;
    }

    /**
     * Gets the value of the payerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayerName() {
        return payerName;
    }

    /**
     * Sets the value of the payerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayerName(String value) {
        this.payerName = value;
    }

    /**
     * Gets the value of the payerNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayerNo() {
        return payerNo;
    }

    /**
     * Sets the value of the payerNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayerNo(String value) {
        this.payerNo = value;
    }

    /**
     * Gets the value of the receiverName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiverName() {
        return receiverName;
    }

    /**
     * Sets the value of the receiverName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiverName(String value) {
        this.receiverName = value;
    }

    /**
     * Gets the value of the receiverNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiverNo() {
        return receiverNo;
    }

    /**
     * Sets the value of the receiverNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiverNo(String value) {
        this.receiverNo = value;
    }

    /**
     * Gets the value of the sourceId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSourceId() {
        return sourceId;
    }

    /**
     * Sets the value of the sourceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSourceId(Integer value) {
        this.sourceId = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setState(Short value) {
        this.state = value;
    }

    /**
     * Gets the value of the taxRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    /**
     * Sets the value of the taxRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTaxRate(BigDecimal value) {
        this.taxRate = value;
    }

    /**
     * Gets the value of the totalPrice property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the value of the totalPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalPrice(BigDecimal value) {
        this.totalPrice = value;
    }

    /**
     * Gets the value of the updateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUpdateTime() {
        return updateTime;
    }

    /**
     * Sets the value of the updateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUpdateTime(XMLGregorianCalendar value) {
        this.updateTime = value;
    }

    /**
     * Gets the value of the updater property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpdater() {
        return updater;
    }

    /**
     * Sets the value of the updater property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdater(String value) {
        this.updater = value;
    }

    /**
     * Gets the value of the yn property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getYn() {
        return yn;
    }

    /**
     * Sets the value of the yn property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setYn(Integer value) {
        this.yn = value;
    }

}
