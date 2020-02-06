package org.apache.jsp.tag.websys;

import com.jeeplus.common.config.Global;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspApplicationContext;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagAdapter;

import org.apache.jasper.runtime.InstanceManagerFactory;
import org.apache.jasper.runtime.JspContextWrapper;
import org.apache.jasper.runtime.JspSourceDependent;
import org.apache.jasper.runtime.PageContextImpl;
import org.apache.jasper.runtime.ProtectedFunctionMapper;
import org.apache.jasper.runtime.TagHandlerPool;
import org.apache.taglibs.standard.functions.Functions;
import org.apache.taglibs.standard.tag.rt.core.IfTag;
import org.apache.taglibs.standard.tag.rt.core.SetTag;
import org.apache.tomcat.InstanceManager;

public final class message_tag extends SimpleTagSupport implements JspSourceDependent {
	private static ProtectedFunctionMapper _jspx_fnmap_0 = ProtectedFunctionMapper.getMapForFunction("fns:getAdminPath", Global.class, "getAdminPath", new Class[0]);
	private static ProtectedFunctionMapper _jspx_fnmap_1 = ProtectedFunctionMapper.getMapForFunction("fn:indexOf", Functions.class, "indexOf", new Class[]{String.class, String.class});

	private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

	private static Map<String, Long> _jspx_dependants = new HashMap(4);
	private JspContext jspContext;
	private Writer _jspx_sout;
	private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
	private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
	private ExpressionFactory _el_expressionfactory;
	private InstanceManager _jsp_instancemanager;
	private String showType;
	private String content;
	private String type;

	static {
		_jspx_dependants.put("/WEB-INF/tlds/menu.tld", Long.valueOf(1495040902485L));
		_jspx_dependants.put("/webpage/include/taglib.jsp", Long.valueOf(1496543662854L));
		_jspx_dependants.put("/WEB-INF/tlds/fns.tld", Long.valueOf(1497955049550L));
		_jspx_dependants.put("/WEB-INF/tlds/shiros.tld", Long.valueOf(1495040902489L));
	}

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

	public String getShowType() {
		return this.showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
		this.jspContext.setAttribute("showType", showType);
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
		this.jspContext.setAttribute("content", content);
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
		this.jspContext.setAttribute("type", type);
	}

	public Map<String, Long> getDependants() {
		return _jspx_dependants;
	}

	private void _jspInit(ServletConfig config) {
		this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(config);
		this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(config);
		this._el_expressionfactory = _jspxFactory.getJspApplicationContext(config.getServletContext()).getExpressionFactory();
		this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(config);
	}

	public void _jspDestroy() {
		this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
		this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
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
		if (getShowType() != null)
			_jspx_page_context.setAttribute("showType", getShowType());
		if (getContent() != null)
			_jspx_page_context.setAttribute("content", getContent());
		if (getType() != null)
			_jspx_page_context.setAttribute("type", getType());
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
					out.write("<!-- 0:隐藏tip, 1隐藏box,不设置显示全部 -->\n");
					out.write("\n");
					out.write("\n");
					if(!this._jspx_meth_c_005fif_005f0(_jspx_page_context)) {
						return;
					}
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

	private boolean _jspx_meth_c_005fset_005f0(PageContext _jspx_page_context)
			throws Throwable {
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

	private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable {
		JspWriter out = _jspx_page_context.getOut();

		IfTag _jspx_th_c_005fif_005f0 = (IfTag) this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
		_jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
		_jspx_th_c_005fif_005f0.setParent(new TagAdapter(this));
		_jspx_th_c_005fif_005f0.setTest(((Boolean) PageContextImpl.proprietaryEvaluate("${not empty content}", Boolean.class, (PageContext) getJspContext(), null, false)).booleanValue());
		int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
		if (_jspx_eval_c_005fif_005f0 != 0) {
			int evalDoAfterBody;
			do {
				out.write(10);
				out.write(9);
				if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
					return true;
				out.write(10);
				out.write(9);
				if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fif_005f0, _jspx_page_context))
					return true;
				out.write(10);
				out.write(9);
				if (_jspx_meth_c_005fif_005f3(_jspx_th_c_005fif_005f0, _jspx_page_context))
					return true;
				out.write(32);
				out.write(10);
				out.write(9);
				if (_jspx_meth_c_005fif_005f4(_jspx_th_c_005fif_005f0, _jspx_page_context))
					return true;
				out.write(10);
				evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
			}
			while (evalDoAfterBody == 2);
		}

		if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
			this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
			throw new SkipPageException();
		}
		this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
		return false;
	}

	private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable {
		JspWriter out = _jspx_page_context.getOut();

		IfTag _jspx_th_c_005fif_005f1 = (IfTag) this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
		_jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
		_jspx_th_c_005fif_005f1.setParent((Tag) _jspx_th_c_005fif_005f0);

		_jspx_th_c_005fif_005f1.setTest(((Boolean) PageContextImpl.proprietaryEvaluate("${not empty type}", Boolean.class, (PageContext) getJspContext(), null, false)));
		int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
		if (_jspx_eval_c_005fif_005f1 != 0) {
			int evalDoAfterBody;
			do {
				out.write(10);
				out.write(9);
				if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
					return true;
				evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
			}
			while (evalDoAfterBody == 2);
		}

		if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
			this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
			throw new SkipPageException();
		}
		this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
		return false;
	}

	private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable {
		_jspx_page_context.getOut();

		SetTag _jspx_th_c_005fset_005f2 = (SetTag) this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
		_jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
		_jspx_th_c_005fset_005f2.setParent((Tag) _jspx_th_c_005fif_005f1);

		_jspx_th_c_005fset_005f2.setVar("ctype");

		_jspx_th_c_005fset_005f2.setValue(PageContextImpl.proprietaryEvaluate("${type}", Object.class, (PageContext) getJspContext(), null, false));
		_jspx_th_c_005fset_005f2.doStartTag();
		if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
			this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f2);
			throw new SkipPageException();
		}
		this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f2);
		return false;
	}

	private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable {
		JspWriter out = _jspx_page_context.getOut();

		IfTag _jspx_th_c_005fif_005f2 = (IfTag) this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
		_jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
		_jspx_th_c_005fif_005f2.setParent((Tag) _jspx_th_c_005fif_005f0);

		_jspx_th_c_005fif_005f2.setTest(((Boolean) PageContextImpl.proprietaryEvaluate("${empty type}", Boolean.class, (PageContext) getJspContext(), null, false)).booleanValue());
		int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
		if (_jspx_eval_c_005fif_005f2 != 0) {
			int evalDoAfterBody;
			do {
				out.write(10);
				out.write(9);
				if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fif_005f2, _jspx_page_context))
					return true;
				out.write(10);
				out.write(9);
				evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
			}
			while (evalDoAfterBody == 2);
		}

		if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
			this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
			throw new SkipPageException();
		}
		this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
		return false;
	}

	private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable {
		_jspx_page_context.getOut();

		SetTag _jspx_th_c_005fset_005f3 = (SetTag) this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
		_jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
		_jspx_th_c_005fset_005f3.setParent((Tag) _jspx_th_c_005fif_005f2);

		_jspx_th_c_005fset_005f3.setVar("ctype");

		_jspx_th_c_005fset_005f3.setValue(PageContextImpl.proprietaryEvaluate("${fn:indexOf(content,'失败') eq -1?'success':'danger'}", Object.class, (PageContext) getJspContext(), _jspx_fnmap_1, false));
		_jspx_th_c_005fset_005f3.doStartTag();
		if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
			this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f3);
			throw new SkipPageException();
		}
		this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f3);
		return false;
	}

	private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable {
		JspWriter out = _jspx_page_context.getOut();

		IfTag _jspx_th_c_005fif_005f3 = (IfTag) this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
		_jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
		_jspx_th_c_005fif_005f3.setParent((Tag) _jspx_th_c_005fif_005f0);

		_jspx_th_c_005fif_005f3.setTest(((Boolean) PageContextImpl.proprietaryEvaluate("${showType == '1'}", Boolean.class, (PageContext) getJspContext(), null, false)).booleanValue());
		int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
		if (_jspx_eval_c_005fif_005f3 != 0) {
			int evalDoAfterBody;
			do {
				out.write("\n");
				out.write("\t\t<div id=\"messageBox\" class=\"alert alert-");
				out.write((String) PageContextImpl.proprietaryEvaluate("${ctype}", String.class, (PageContext) getJspContext(), null, false));
				out.write("\">\n");
				out.write("\t\t<button data-dismiss=\"alert\" class=\"close\">×</button>");
				out.write((String) PageContextImpl.proprietaryEvaluate("${content}", String.class, (PageContext) getJspContext(), null, false));
				out.write("</div>\n");
				out.write("\t");
				evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
			}
			while (evalDoAfterBody == 2);
		}

		if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
			this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
			throw new SkipPageException();
		}
		this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
		return false;
	}

	private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable {
		JspWriter out = _jspx_page_context.getOut();

		IfTag _jspx_th_c_005fif_005f4 = (IfTag) this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
		_jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
		_jspx_th_c_005fif_005f4.setParent((Tag) _jspx_th_c_005fif_005f0);

		_jspx_th_c_005fif_005f4.setTest(((Boolean) PageContextImpl.proprietaryEvaluate("${showType != '1'}", Boolean.class, (PageContext) getJspContext(), null, false)).booleanValue());
		int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
		if (_jspx_eval_c_005fif_005f4 != 0) {
			int evalDoAfterBody;
			do {
				out.write("\n");
				out.write("\t\t<script type=\"text/javascript\">\n");
				out.write("\t\t\ttop.layer.msg(\"");
				out.write((String) PageContextImpl.proprietaryEvaluate("${content}", String.class, (PageContext) getJspContext(), null, false));
				out.write("\", {icon: \"");
				out.write((String) PageContextImpl.proprietaryEvaluate("${ctype=='success'? 1:0}", String.class, (PageContext) getJspContext(), null, false));
				out.write("\"});\n");
				out.write("\t\t</script>\n");
				out.write("\t");
				evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
			}
			while (evalDoAfterBody == 2);
		}

		if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
			this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
			throw new SkipPageException();
		}
		this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
		return false;
	}
}