package org.apache.jsp.tag.websys;

import com.jeeplus.common.config.Global;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.el.ExpressionFactory;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.tagext.TagAdapter;

import org.apache.jasper.runtime.InstanceManagerFactory;
import org.apache.jasper.runtime.JspContextWrapper;
import org.apache.jasper.runtime.JspSourceDependent;
import org.apache.jasper.runtime.PageContextImpl;
import org.apache.jasper.runtime.ProtectedFunctionMapper;
import org.apache.jasper.runtime.TagHandlerPool;
import org.apache.taglibs.standard.tag.rt.core.SetTag;
import org.apache.tomcat.InstanceManager;

public final class iconselect_tag extends SimpleTagSupport implements JspSourceDependent {
	private static ProtectedFunctionMapper _jspx_fnmap_0 = ProtectedFunctionMapper.getMapForFunction("fns:getAdminPath", Global.class, "getAdminPath", new Class[0]);

	private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

	private static Map<String, Long> _jspx_dependants = new HashMap(4);
	private JspContext jspContext;
	private Writer _jspx_sout;
	private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
	private ExpressionFactory _el_expressionfactory;
	private InstanceManager _jsp_instancemanager;
	private String id;
	private String name;
	private String value;

	static {
		_jspx_dependants.put("/WEB-INF/tlds/menu.tld", Long.valueOf(1495040902485L));
		_jspx_dependants.put("/webpage/include/taglib.jsp", Long.valueOf(1496543662854L));
		_jspx_dependants.put("/WEB-INF/tlds/fns.tld", Long.valueOf(1497955049550L));
		_jspx_dependants.put("/WEB-INF/tlds/shiros.tld", Long.valueOf(1495040902489L));
	}

	//有误JspContextWrapper可能因tomcat9的问题。super.getParent()
	public void setJspContext(JspContext ctx) {
		super.setJspContext(ctx);
		ArrayList _jspx_nested = null;
		ArrayList _jspx_at_begin = null;
		ArrayList _jspx_at_end = null;
		this.jspContext = new JspContextWrapper(ctx, _jspx_nested, _jspx_at_begin, _jspx_at_end, null);
	}

	public JspContext getJspContext() {
		return this.jspContext;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
		this.jspContext.setAttribute("id", id);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
		this.jspContext.setAttribute("name", name);
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
		this.jspContext.setAttribute("value", value);
	}

	public Map<String, Long> getDependants() {
		return _jspx_dependants;
	}

	private void _jspInit(ServletConfig config) {
		this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(config);
		this._el_expressionfactory = _jspxFactory.getJspApplicationContext(config.getServletContext()).getExpressionFactory();
		this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(config);
	}

	public void _jspDestroy() {
		this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
	}

	public void doTag() throws JspException, IOException {
		PageContext _jspx_page_context = (PageContext) this.jspContext;
		HttpServletRequest var10000 = (HttpServletRequest)_jspx_page_context.getRequest();
		HttpServletResponse var10 = (HttpServletResponse)_jspx_page_context.getResponse();
		_jspx_page_context.getSession();
		_jspx_page_context.getServletContext();
		ServletConfig config = _jspx_page_context.getServletConfig();
		JspWriter out = this.jspContext.getOut();
		_jspInit(config);
		this.jspContext.getELContext().putContext(JspContext.class, this.jspContext);
		if (getId() != null)
			_jspx_page_context.setAttribute("id", getId());
		if (getName() != null)
			_jspx_page_context.setAttribute("name", getName());
		if (getValue() != null)
			_jspx_page_context.setAttribute("value", getValue());
		try {
			out.write(10);
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			if(!this._jspx_meth_c_005fset_005f0(_jspx_page_context)) {
				out.write(10);
				if(!this._jspx_meth_c_005fset_005f1(_jspx_page_context)) {
					out.write("\n");
					out.write("\n");
					out.write("\n");
					out.write("\n");
					out.write("<i id=\"");
					out.write((String) PageContextImpl.proprietaryEvaluate("${id}", String.class, (PageContext) getJspContext(), null, false));
					out.write("Icon\" class=\"");
					out.write((String) PageContextImpl.proprietaryEvaluate("${not empty value?value:' hide'}", String.class, (PageContext) getJspContext(), null, false));
					out.write("\"></i>&nbsp;<span id=\"");
					out.write((String) PageContextImpl.proprietaryEvaluate("${id}", String.class, (PageContext) getJspContext(), null, false));
					out.write("IconLabel\">");
					out.write((String) PageContextImpl.proprietaryEvaluate("${not empty value?value:'无'}", String.class, (PageContext) getJspContext(), null, false));
					out.write("</span>&nbsp;\n");
					out.write("<input id=\"");
					out.write((String) PageContextImpl.proprietaryEvaluate("${id}", String.class, (PageContext) getJspContext(), null, false));
					out.write("\" name=\"");
					out.write((String) PageContextImpl.proprietaryEvaluate("${name}", String.class, (PageContext) getJspContext(), null, false));
					out.write("\" type=\"hidden\" value=\"");
					out.write((String) PageContextImpl.proprietaryEvaluate("${value}", String.class, (PageContext) getJspContext(), null, false));
					out.write("\"/><a id=\"");
					out.write((String) PageContextImpl.proprietaryEvaluate("${id}", String.class, (PageContext) getJspContext(), null, false));
					out.write("Button\" href=\"javascript:\" class=\"btn btn-primary\">选择</a>&nbsp;&nbsp;\n");
					out.write("<input id=\"");
					out.write((String) PageContextImpl.proprietaryEvaluate("${id}", String.class, (PageContext) getJspContext(), null, false));
					out.write("clear\" class=\"btn btn-default\" type=\"button\" value=\"清除\" onclick=\"clear()\"/>\n");
					out.write("<script type=\"text/javascript\">\n");
					out.write("$(document).ready(function(){\n");
					out.write("\t$(\"#");
					out.write((String) PageContextImpl.proprietaryEvaluate("${id}", String.class, (PageContext) getJspContext(), null, false));
					out.write("Button\").click(function(){\n");
					out.write("\t\n");
					out.write("\t\ttop.layer.open({\n");
					out.write("\t\t\ttype: 2, \n");
					out.write("\t\t\ttitle:\"选择图标\",\n");
					out.write("\t\t\tarea: ['700px',  $(top.document).height()-180+\"px\"],\n");
					out.write("\t\t    content: '");
					out.write((String) PageContextImpl.proprietaryEvaluate("${ctx}", String.class, (PageContext) getJspContext(), null, false));
					out.write("/tag/iconselect?value=\"+$(\"#");
					out.write((String) PageContextImpl.proprietaryEvaluate("${id}", String.class, (PageContext) getJspContext(), null, false));
					out.write("\").val()',\n");
					out.write("\t\t    btn: ['确定', '关闭'],\n");
					out.write("\t\t    yes: function(index, layero){ //或者使用btn1\n");
					out.write("\t\t    \tvar icon = layero.find(\"iframe\")[0].contentWindow.$(\"#icon\").val();\n");
					out.write("            \t$(\"#");
					out.write((String) PageContextImpl.proprietaryEvaluate("${id}", String.class, (PageContext) getJspContext(), null, false));
					out.write("Icon\").attr(\"class\", icon);\n");
					out.write("                $(\"#");
					out.write((String) PageContextImpl.proprietaryEvaluate("${id}", String.class, (PageContext) getJspContext(), null, false));
					out.write("IconLabel\").text(icon);\n");
					out.write("                $(\"#");
					out.write((String) PageContextImpl.proprietaryEvaluate("${id}", String.class, (PageContext) getJspContext(), null, false));
					out.write("\").val(icon);\n");
					out.write("                top.layer.close(index);\n");
					out.write("\t\t    },cancel: function(index){ //或者使用btn2\n");
					out.write("\t\t    \t\n");
					out.write("\t\t    }\n");
					out.write("\t\t});\n");
					out.write("\t});\n");
					out.write("\t$(\"#");
					out.write((String) PageContextImpl.proprietaryEvaluate("${id}", String.class, (PageContext) getJspContext(), null, false));
					out.write("clear\").click(function(){\n");
					out.write("\t\t $(\"#");
					out.write((String) PageContextImpl.proprietaryEvaluate("${id}", String.class, (PageContext) getJspContext(), null, false));
					out.write("Icon\").attr(\"class\", \"icon- hide\");\n");
					out.write("         $(\"#");
					out.write((String) PageContextImpl.proprietaryEvaluate("${id}", String.class, (PageContext) getJspContext(), null, false));
					out.write("IconLabel\").text(\"无\");\n");
					out.write("         $(\"#");
					out.write((String) PageContextImpl.proprietaryEvaluate("${id}", String.class, (PageContext) getJspContext(), null, false));
					out.write("\").val(\"\");\n");
					out.write("\n");
					out.write("\t});\n");
					out.write("})\n");
					out.write("</script>");
					return;
				}
			}
		} catch (Throwable t) {
			if ((t instanceof SkipPageException))
				throw ((SkipPageException) t);
			if ((t instanceof IOException))
				throw ((IOException) t);
			if ((t instanceof IllegalStateException))
				throw ((IllegalStateException) t);
			if ((t instanceof JspException))
				throw ((JspException) t);
			throw new JspException(t);
		} finally {
			this.jspContext.getELContext().putContext(JspContext.class, super.getJspContext());
			((JspContextWrapper) this.jspContext).syncEndTagFile();
			_jspDestroy();
		}
		this.jspContext.getELContext().putContext(JspContext.class, super.getJspContext());
		((JspContextWrapper) this.jspContext).syncEndTagFile();
		_jspDestroy();
	}

	private boolean _jspx_meth_c_005fset_005f0(PageContext _jspx_page_context) throws Throwable {
		_jspx_page_context.getOut();

		SetTag _jspx_th_c_005fset_005f0 = (SetTag) this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
		_jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
		_jspx_th_c_005fset_005f0.setParent(new TagAdapter(this));
		_jspx_th_c_005fset_005f0.setVar("ctx");

		_jspx_th_c_005fset_005f0.setValue(PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}${fns:getAdminPath()}", Object.class, (PageContext) getJspContext(), _jspx_fnmap_0, false));
		_jspx_th_c_005fset_005f0.doStartTag();
		if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
			this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
			throw new SkipPageException();
		}
		this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
		return false;
	}

	private boolean _jspx_meth_c_005fset_005f1(PageContext _jspx_page_context) throws Throwable {
		_jspx_page_context.getOut();

		SetTag _jspx_th_c_005fset_005f1 = (SetTag) this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
		_jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
		_jspx_th_c_005fset_005f1.setParent(new TagAdapter(this));
		_jspx_th_c_005fset_005f1.setVar("ctxStatic");

		_jspx_th_c_005fset_005f1.setValue(PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}/static", Object.class, (PageContext) getJspContext(), null, false));
		_jspx_th_c_005fset_005f1.doStartTag();
		if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
			this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
			throw new SkipPageException();
		}
		this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
		return false;
	}
}