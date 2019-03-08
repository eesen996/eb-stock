$.extend($.validator.messages, {
required:"<font color='red'>\u5FC5\u586B\u5B57\u6BB5</font>",
remote:"<font color='red'>\u8BE5\u4FE1\u606F\u7CFB\u7EDF\u5DF2\u5B58\u5728\uFF0C\u8BF7\u4E0D\u8981\u8F93\u5165\u91CD\u590D\u7684\u4FE1\u606F</font>",
email:"<font color='red'>\u8BF7\u8F93\u5165\u6B63\u786E\u683C\u5F0F\u7684\u7535\u5B50\u90AE\u4EF6</font>",
url:"<font color='red'>\u8BF7\u8F93\u5165\u5408\u6CD5\u7684\u7F51\u5740</font>",
date:"<font color='red'>\u8BF7\u8F93\u5165\u5408\u6CD5\u7684\u65E5\u671F</font>",
dateISO:"<font color='red'>\u8BF7\u8F93\u5165\u5408\u6CD5\u7684\u65E5\u671F (ISO).</font>",
number:"<font color='red'>\u8BF7\u8F93\u5165\u5408\u6CD5\u7684\u6570\u5B57</font>",
digits:"<font color='red'>\u53EA\u80FD\u8F93\u5165\u6574\u6570</font>",
creditcard:"<font color='red'>\u8BF7\u8F93\u5165\u5408\u6CD5\u7684\u4FE1\u7528\u5361\u53F7</font>",
equalTo:"<font color='red'>\u8BF7\u518D\u6B21\u8F93\u5165\u76F8\u540C\u7684\u503C</font>",
accept:"<font color='red'>\u8BF7\u8F93\u5165\u62E5\u6709\u5408\u6CD5\u540E\u7F00\u540D\u7684\u5B57\u7B26\u4E32</font>",
maxlength:$.validator.format("<font color='red'>\u8BF7\u8F93\u5165\u4E00\u4E2A\u957F\u5EA6\u6700\u591A\u662F {0} \u7684\u5B57\u7B26\u4E32</font>"),
minlength:$.validator.format("<font color='red'>\u8BF7\u8F93\u5165\u4E00\u4E2A\u957F\u5EA6\u6700\u5C11\u662F {0} \u7684\u5B57\u7B26\u4E32</font>"),
rangelength:$.validator.format("<font color='red'>\u8BF7\u8F93\u5165\u4E00\u4E2A\u957F\u5EA6\u4ECB\u4E8E {0} \u548C {1} \u4E4B\u95F4\u7684\u5B57\u7B26\u4E32</font>"),
range:$.validator.format("<font color='red'>\u8BF7\u8F93\u5165\u4E00\u4E2A\u4ECB\u4E8E {0} \u548C {1} \u4E4B\u95F4\u7684\u503C</font>"),
max:$.validator.format("<font color='red'>\u8BF7\u8F93\u5165\u4E00\u4E2A\u6700\u5927\u4E3A {0} \u7684\u503C</font>"),
min:$.validator.format("<font color='red'>\u8BF7\u8F93\u5165\u4E00\u4E2A\u6700\u5C0F\u4E3A {0} \u7684\u503C</font>"),
oldPass:"<font color='red'>\u9A8C\u8BC1\u65E7\u5BC6\u7801\u4E0D\u6B63\u786E</font>"
});

/* 手机号 */
jQuery.validator.addMethod("ismobile", function(value, element) {  
    var length = value.length;     
    //var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
    var mobile = /^1[3456789]\d{9}$/;
    return (length == 11 && mobile.exec(value))? true:false;  
}, "<font color='red'>\u8BF7\u8F93\u5165\u6709\u6548\u7684\u624B\u673A\u53F7</font>");

/* 中文名 */
jQuery.validator.addMethod("isChinaName", function(value, element) {  
    var chinaName = /^[\u4E00-\u9FA5]{1,6}$/;
    return (chinaName.exec(value))? true:false;  
}, "<font color='red'>\u8BF7\u8F93\u5165\u4E2D\u6587\u540D</font>");

/* 身份证号 */
jQuery.validator.addMethod("isCardNo", function(value, element) {  
	var length = value.length;
    var cardNo = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    return (length == 18 &&cardNo.exec(value))? true:false;  
}, "<font color='red'>\u8BF7\u8F93\u5165\u6B63\u786E\u7684\u8EAB\u4EFD\u8BC1\u53F7\u7801</font>");




