package cn.wmc.dormitory.web.struts.service;

public class ResponseBean {
	private Object object;
	private int status;
	private String statusMsg;
	public ResponseBean() {}
	public ResponseBean(int status, String statusMsg) {
		super();
		this.status = status;
		this.statusMsg = statusMsg;
	}
	public ResponseBean(Object object, int status, String statusMsg) {
		this.object = object;
		this.status = status;
		this.statusMsg = statusMsg;
	}
	public Object getObject() {
		return object;
	}
	public int getStatus() {
		return status;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	@Override
	public String toString() {
		return "ResponseBean [object=" + object + ", status=" + status + ", statusMsg=" + statusMsg + "]";
	}
	

}
