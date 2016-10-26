private EditText mPhoneTv;

mPhoneTv.addTextChangedListener(new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            formatePhoneNum(s);
        }
    });
/** * 最近一次显示的电话号码 */ 
private String lastShowTxt="";

/**
 * 格式化手机号码 132 1111 1111
 */
private boolean formatePhoneNum(Editable s){
    Log.d("formatePhoneNum","lastShowTxt_1:"+lastShowTxt);
    Log.d("formatePhoneNum","s:"+s.toString());
    boolean ret=false;
    if(s==null){
        return ret;
    }
    String oriText=s.toString();
    if(NullUtils.isNull(oriText)){
        return ret;
    }
    int oriSlectionIndex=mPhoneTv.getSelectionEnd();//未格式化前光标所在位置
    Log.d("formatePhoneNum","oriSlectionIndex:"+oriSlectionIndex);
    int thisSelectionIndex=oriSlectionIndex;//本次文字改变后光标应该在的位置
    String formated=hasFomated(oriText);
    Log.d("formatePhoneNum","formated:"+formated);
    if(!oriText.equals(formated)){
        ret=true;
    }
    Log.d("formatePhoneNum","ret:"+ret);
    if(ret && oriSlectionIndex>0){
        int offset=0;
        String oriTextAtOriSelection=String.valueOf(oriText.charAt(oriSlectionIndex-1));
        String formatedTextAtOriSelection="";
        Log.d("formatePhoneNum","oriTextAtOriSelection:"+oriTextAtOriSelection);
        Log.d("formatePhoneNum","lastShowTxt:"+lastShowTxt);
        if((oriText.length()>formated.length())||(oriText.length()<lastShowTxt.length())){
            if(NullUtils.isNull(oriTextAtOriSelection)){
                offset=-1;//如果原始长度大于格式化后的或者原始长度小于上一次显示的文字长度，说明是删除空格操作，光标要向前多移动一位，跳过空格
            }
        }else if(oriText.length()<formated.length()){
            offset=1;//如果原始原始长度小于格式化后的 ，说明是插入空格入操作，光标要向后多移动移动
        }
       Log.d("formatePhoneNum","offset:"+offset);
        if(formatedTextAtOriSelection.length()>=oriSlectionIndex){
            formatedTextAtOriSelection=String.valueOf(oriText.charAt(oriSlectionIndex-1));
        }
       Log.d("formatePhoneNum","formatedTextAtOriSelection:"+formatedTextAtOriSelection);
        //设置格式化后光标所在的位置
        if(!oriTextAtOriSelection.equals(formatedTextAtOriSelection)){
            thisSelectionIndex=oriSlectionIndex+offset;//设置格式化后光标所在的位置
        }else {
            thisSelectionIndex=oriSlectionIndex;//设置格式化后光标所在的位置
        }
    }else{
        thisSelectionIndex=oriSlectionIndex;//设置格式化后光标所在的位置
    }
    Log.d("formatePhoneNum","thisSelectionIndex:"+thisSelectionIndex);
    //统一更改文字以及移动光标
    if(ret){
        mPhoneTv.setText(formated);
        mPhoneTv.setSelection((thisSelectionIndex<=formated.length())?thisSelectionIndex:formated.length());
    }
    lastShowTxt=formated;
    return ret;
}
