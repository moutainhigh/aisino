
package com.aisino.cxf.client;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for eiActualDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eiActualDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="businessId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="detailType" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="hashTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="imei" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="num" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="productName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="refId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="sourceId" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="spec" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="taxRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="unit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="wid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="yn" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eiActualDetail", propOrder = {
    "amount",
    "businessId",
    "createTime",
    "detailType",
    "hashTime",
    "id",
    "imei",
    "num",
    "price",
    "productName",
    "refId",
    "sourceId",
    "spec",
    "state",
    "taxRate",
    "unit",
    "wid",
    "yn"
})
public class EiActualDetail {

    protected BigDecimal amount;
    protected String businessId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createTime;
    protected Integer detailType;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar hashTime;
    protected Long id;
    protected String imei;
    protected BigDecimal num;
    protected BigDecimal price;
    protected String productName;
    protected Long refId;
    protected Short sourceId;
    protected String spec;
    protected Integer state;
    protected BigDecimal taxRate;
    protected String unit;
    protected String wid;
    protected Short yn;

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAmount(BigDecimal value) {
        this.amount = value;
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
     * Gets the value of the detailType property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDetailType() {
        return detailType;
    }

    /**
     * Sets the value of the detailType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDetailType(Integer value) {
        this.detailType = value;
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
     * Gets the value of the imei property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImei() {
        return imei;
    }

    /**
     * Sets the value of the imei property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImei(String value) {
        this.imei = value;
    }

    /**
     * Gets the value of the num property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNum() {
        return num;
    }

    /**
     * Sets the value of the num property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNum(BigDecimal value) {
        this.num = value;
    }

    /**
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPrice(BigDecimal value) {
        this.price = value;
    }

    /**
     * Gets the value of the productName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the value of the productName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductName(String value) {
        this.productName = value;
    }

    /**
     * Gets the value of the refId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getRefId() {
        return refId;
    }

    /**
     * Sets the value of the refId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setRefId(Long value) {
        this.refId = value;
    }

    /**
     * Gets the value of the sourceId property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getSourceId() {
        return sourceId;
    }

    /**
     * Sets the value of the sourceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setSourceId(Short value) {
        this.sourceId = value;
    }

    /**
     * Gets the value of the spec property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpec() {
        return spec;
    }

    /**
     * Sets the value of the spec property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpec(String value) {
        this.spec = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setState(Integer value) {
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
     * Gets the value of the unit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets the value of the unit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnit(String value) {
        this.unit = value;
    }

    /**
     * Gets the value of the wid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWid() {
        return wid;
    }

    /**
     * Sets the value of the wid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWid(String value) {
        this.wid = value;
    }

    /**
     * Gets the value of the yn property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getYn() {
        return yn;
    }

    /**
     * Sets the value of the yn property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setYn(Short value) {
        this.yn = value;
    }

}
