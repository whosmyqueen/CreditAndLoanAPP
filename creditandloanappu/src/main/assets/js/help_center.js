;(function($){
	arr=[
		{
			"title":"Q:",
			"t":"如何下载“正好分期”客户端APP?",
			"center":"A:",
			"c":"1）关注“正好分期”公众号，点击分期APP，可下载Android、Ios对应的APP. 2）Android各大应用市场，AppStore搜索“正好分期”下载安装即可。",
		},
		{
			"title":"Q:",
			"t":"如何注册/登录“正好分期”账号？",
			"center":"A:",
			"c":"下载安装“正好分期”APP，在登录页面点击“新用户注册”即可完成注册。在登录页面输入正确的账号和密码即可登录完成。",
		},
		{
			"title":"Q:",
			"t":"无法正常收到验证码？",
			"center":"A:",
			"c":"1）点击重新获取再次获取验证码。2）请查看手机设置，是否有软件拦截。3）运营商有限制，10分钟内获取短信不能超过3次。请稍后再试。 4）以上方式都通过还无法获取，请您联系客服咨询。",
		},
		{
			"title":"Q:",
			"t":"无法正常登陆“正好分期”APP？",
			"center":"A:",
			"c":"输入账号密码却无法正常登陆，您可以点击忘记密码，填写找回密码相关信息，即可重新设置密码。",
		},
		{
			"title":"Q:",
			"t":"如何在线申请办理“正好分期”业务？",
			"center":"A:",
			"c":"打开“正好分期”客户端APP，注册“正好分期”账号，点击“扫码”图标即可进行分期业务。二维码需要向正好分期合作商户索要哦！完成分期资料填写任务后，我们的信审专员将会尽快联系您，请保持您的手机畅通哦。",
		},
		{
			"title":"Q:",
			"t":"办理分期业务使用亲人、朋友的手机办理是否可以？",
			"center":"A:",
			"c":"根据我司产品大纲要求，必须申请人本人常用号码，不接受其他人的手机号码代为办理。",
		},
		{
			"title":"Q:",
			"t":"什么是“快速分期”？",
			"center":"A:",
			"c":"“快速分期”是“正好分期”APP为了用户能更方便快捷的办理分期业务定义的。当您有被驳回的订单或者资料未填写完成的订单，可点击“快速分期”快速完成资料填写与修改。",
		},
		{
			"title":"Q:",
			"t":"如何查看分期业务办理进度？",
			"center":"A:",
			"c":"打开“正好分期”APP，点击“订单”，即可查询所有办理的订单业务，点击订单项的“进度查询”即可查看具体业务办理进度。",
		},
		{
			"title":"Q:",
			"t":"审核拒绝是什么原因？",
			"center":"A:",
			"c":"1）个人资料不符合要求，比如说证件号码、联系人信息等。2）填写资料未虚假信息或涉嫌欺诈等",
		},
		{
			"title":"Q:",
			"t":"如果审核拒绝后是否可以再次申请，再次申请是多长？",
			"center":"A:",
			"c":"不同的拒绝原因再次进件时间也不相同，拒绝后有随时可以申请的，也有终身不得进件的。具体限制时间，待公司政策制定后，在系统提交时会有提示。",
		},
		{
			"title":"Q:",
			"t":"审核拒绝是什么原因？",
			"center":"A:",
			"c":"1）个人资料不符合要求，比如说证件号码、联系人信息等。2）填写资料未虚假信息或涉嫌欺诈等",
		},
		{
			"title":"Q:",
			"t":"一个账号同时可以办理多个分期业务么？",
			"center":"A:",
			"c":"一个账号客户端账号原则上只接受一个分期业务申请、注册，若该单为不通过,在限制时间以后此账号可继续申请；若是撤销、取消状态，此账号可继续申请；若该单前次审批结果为通过并注册放款，正常还款6期后可再次申请。",
		},
		{
			"title":"Q:",
			"t":"如何更换已绑定的代扣银行卡？",
			"center":"A:",
			"c":"请联系为您办单的客户代表处理，告知客户代表变更前后的银行卡信息，由客户代表联系后台工作人员处理。",
		},
		{
			"title":"Q:",
			"t":"如何进行退货申请？",
			"center":"A:",
			"c":"请联系为您办单的客户代表处理，有商品质量问题7天内（从注册日开始算起）允许发起退货申请。退货成功后，客户需把商品退回商户，商户出具退货凭证并退回首付款，商户将商品退款（即贷款本金）退还我司。",
		},
		{
			"title":"Q:",
			"t":"重复划扣后是否可以申请退款？",
			"center":"A:",
			"c":"如果客户银行卡划扣不成功，存对公以后，银行卡又再次划扣的情况，重复划扣的金额会自动进入下一期还款。客户也可以联系客户代表提起退款申请。",
		},
		{
			"title":"Q:",
			"t":"是否可以申请提前结清？",
			"center":"A:",
			"c":"请联系为您办单的客户代表处理，借款起始日起3个月后可以申请提前结清。借款起始日起3个月内，不接受提前结清申请；如因特殊情况客户需要提前结清的，必须经我司同意，申请特殊提前结清不退服务费。",
		},
		{
			"title":"Q:",
			"t":"是否可以存对公进行还款？",
			"center":"A:",
			"c":"客户在划扣失败避免逾期的紧急情况下，可以将本期月还款金额存入公司对公账户或者支付宝账户。客户存对公后需将还款凭证提供给客户代表.1、支付宝账号为：zhzj@zhphfinance.com. 2、对公账户：开户银行：农业银行成都贝森南路支行账户名称：成都正合联众网络科技有限公司.银行账号：22815201040003765",
		},
		{
			"title":"Q:",
			"t":"是否可以取消保险？",
			"center":"A:",
			"c":"购买保险的客户，如需取消保险，需按期还完3期后方可申请，当月申请次月生效。申请方式可直接拨打客服热线电话：400-702-6677。",
		}
	]

	function Arr(arr){
		var str="";
		$.each(arr,function(k,v){
			//console.log(k)
		 	 str += "<h2>"+
			 	 "<p>"+
			 	 	"<a data-na='"+k+"' href='#' class='a'><b><span >"+v.title+"</span>"+v.t+"</b></a>"+
			 	 "</p>"+
		 	 "</h2>";
		 })
		
		$(".main").html(str);
		$(".a").on("click",function(){
			
			 da=($(this).attr("data-na"))
			 //alert(da)
			 location.href='detail.html?a='+da;
			//$(".m").eq(da).hide()
			//Arr1(arr,da)
		})
	}

	Arr(arr)

})(Zepto)
