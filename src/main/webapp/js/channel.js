/**
 * Created by pcy on 2017/1/20.
 */
   $.extend($.fn.validatebox.defaults.rules, {
        chinese: {
            validator: function(value){
                var rex=/^[\u4e00-\u9fa5]+$/;
                if(rex.test(value)) {return true;}else{return false;}
            },
            message: '请输入中文'
        }
    });