/**
 * 富文本
 *
 */

var RE = {};

RE.editor = document.getElementById('editor');

RE.showHtml = document.getElementById('showHtml');

document.addEventListener("selectionchange", function() { RE.backuprange(); });

// Initializations
RE.callback = function() {
    window.location.href = "re-callback://" + encodeURI(RE.getHtml());
}

RE.setHtml = function(contents) {
    RE.editor.innerHTML = decodeURIComponent(contents.replace(/\+/g, '%20'));
}

RE.setShow = function(contents){
    RE.showHtml.innerHTML = decodeURIComponent(contents.replace(/\+/g, '%20'));
}

RE.getHtml = function() {
    return RE.editor.innerHTML;
}

RE.getText = function() {
    return RE.editor.innerText;
}

RE.setBaseTextColor = function(color) {
    RE.editor.style.color  = color;
}

RE.setBaseFontSize = function(size) {
    RE.editor.style.fontSize = size;
}

RE.setShowFontSize = function(size) {
    RE.editor.style.fontSize = size;
}

RE.setPadding = function(left, top, right, bottom) {
  RE.editor.style.paddingLeft = left;
  RE.editor.style.paddingTop = top;
  RE.editor.style.paddingRight = right;
  RE.editor.style.paddingBottom = bottom;
}

RE.setShowPadding = function(left, top, right, bottom) {
  RE.showHtml.style.paddingLeft = left;
  RE.showHtml.style.paddingTop = top;
  RE.showHtml.style.paddingRight = right;
  RE.showHtml.style.paddingBottom = bottom;
}


RE.setBackgroundColor = function(color) {
    document.body.style.backgroundColor = color;
}

RE.setBackgroundImage = function(image) {
    RE.editor.style.backgroundImage = image;
}

RE.setWidth = function(size) {
    RE.editor.style.minWidth = size;
}

RE.setHeight = function(size) {
    document.body.style.minHeight = size;
}

RE.setTextAlign = function(align) {
    RE.editor.style.textAlign = align;
}

RE.setVerticalAlign = function(align) {
    RE.editor.style.verticalAlign = align;
}

RE.setPlaceholder = function(placeholder) {
    RE.editor.setAttribute("placeholder", placeholder);
}

RE.undo = function() {
    document.execCommand('undo', false, null);
}

RE.redo = function() {
    document.execCommand('redo', false, null);
}

RE.setBold = function() {
    document.execCommand('bold', false, null);
}

RE.setItalic = function() {
    document.execCommand('italic', false, null);
}

RE.setSubscript = function() {
    document.execCommand('subscript', false, null);
}

RE.setSuperscript = function() {
    document.execCommand('superscript', false, null);
}

RE.setStrikeThrough = function() {
    document.execCommand('strikeThrough', false, null);
}

RE.setUnderline = function() {
    document.execCommand('underline', false, null);
}

RE.setBullets = function() {
    document.execCommand('InsertUnorderedList', false, null);
}

RE.setNumbers = function() {
    document.execCommand('InsertOrderedList', false, null);
}

RE.setTextColor = function(color) {
    RE.restorerange();
    document.execCommand("styleWithCSS", null, true);
    document.execCommand('foreColor', false, color);
    document.execCommand("styleWithCSS", null, false);
}

RE.setTextBackgroundColor = function(color) {
    RE.restorerange();
    document.execCommand("styleWithCSS", null, true);
    document.execCommand('hiliteColor', false, color);
    document.execCommand("styleWithCSS", null, false);
}

RE.setFontSize = function(fontSize){
    document.execCommand("fontSize", false, fontSize);
}

RE.setHeading = function(heading) {
    document.execCommand('formatBlock', false, '<h'+heading+'>');
}

RE.setIndent = function() {
    document.execCommand('indent', false, null);
}

RE.setOutdent = function() {
    document.execCommand('outdent', false, null);
}

RE.setJustifyLeft = function() {
    document.execCommand('justifyLeft', false, null);
}

RE.setJustifyCenter = function() {
    document.execCommand('justifyCenter', false, null);
}

RE.setJustifyRight = function() {
    document.execCommand('justifyRight', false, null);
}

RE.insertImage = function(url, alt) {
    var html = '<p><img src="' + url + '"/></p>';
    RE.insertHTML(html);
}

RE.insertHTML = function(html) {
    RE.restorerange();
    document.execCommand('insertHTML', false, html);
}

//图片添加点击事件
RE.setImageClick = function(){
    window.control.log('绑定图片点击监听');
    var objs = document.getElementsByTagName("img");
    for(var i=0;i<objs.length;i++){
        objs[i].onclick=function(){
            window.control.clickImage(this.src);
        }
    }
}

RE.setTodo = function(text) {
    var html = '<input type="checkbox" name="'+ text +'" value="'+ text +'"/> &nbsp;';
    document.execCommand('insertHTML', false, html);
}

RE.prepareInsert = function() {
    RE.backuprange();
}

RE.backuprange = function(){
    var selection = window.getSelection();
    if (selection.rangeCount > 0) {
      var range = selection.getRangeAt(0);
      RE.currentSelection = {
          "startContainer": range.startContainer,
          "startOffset": range.startOffset,
          "endContainer": range.endContainer,
          "endOffset": range.endOffset};
    }
}

RE.restorerange = function(){
    var selection = window.getSelection();
    selection.removeAllRanges();
    var range = document.createRange();
    range.setStart(RE.currentSelection.startContainer, RE.currentSelection.startOffset);
    range.setEnd(RE.currentSelection.endContainer, RE.currentSelection.endOffset);
    selection.addRange(range);
}

RE.enabledEditingItems = function(e) {
    var items = [];
    if (document.queryCommandState('bold')) {
        items.push('bold');
    }
    if (document.queryCommandState('italic')) {
        items.push('italic');
    }
    if (document.queryCommandState('subscript')) {
        items.push('subscript');
    }
    if (document.queryCommandState('superscript')) {
        items.push('superscript');
    }
    if (document.queryCommandState('strikeThrough')) {
        items.push('strikeThrough');
    }
    if (document.queryCommandState('underline')) {
        items.push('underline');
    }
    if (document.queryCommandState('insertOrderedList')) {
        items.push('orderedList');
    }
    if (document.queryCommandState('insertUnorderedList')) {
        items.push('unorderedList');
    }
    if (document.queryCommandState('justifyCenter')) {
        items.push('justifyCenter');
    }
    if (document.queryCommandState('justifyFull')) {
        items.push('justifyFull');
    }
    if (document.queryCommandState('justifyLeft')) {
        items.push('justifyLeft');
    }
    if (document.queryCommandState('justifyRight')) {
        items.push('justifyRight');
    }
    if (document.queryCommandState('insertHorizontalRule')) {
        items.push('horizontalRule');
    }
    var formatBlock = document.queryCommandValue('formatBlock');
    if (formatBlock.length > 0) {
        items.push(formatBlock);
    }

    window.location.href = "re-state://" + encodeURI(items.join(','));
}

RE.focus = function() {
    var range = document.createRange();
    range.selectNodeContents(RE.editor);
    range.collapse(false);
    var selection = window.getSelection();
    selection.removeAllRanges();
    selection.addRange(range);
    RE.editor.focus();
}

RE.focusFirst = function() {
   window.control.log('my is js');
   var selection = window.getSelection();
   if (selection.rangeCount > 0) {
        window.control.log('my is js11111111');
   }else{
        window.control.log('my is js22222222');
        var range = document.createRange();
        range.selectNodeContents(RE.editor);
        range.collapse(false);
        var selection = window.getSelection();
        selection.removeAllRanges();
        selection.addRange(range);
        RE.editor.focus();
   }

}

RE.blurFocus = function() {
    RE.editor.blur();
}

RE.removeFormat = function() {
    execCommand('removeFormat', false, null);
}

RE.changeVideoSize = function(){
    var test = document.getElementsByTagName('video');
    alert(test.length);
}

//设置加载图片错误回调
RE.setLoadImgError = function(){
    var imgs = document.getElementsByTagName("img");
    if(imgs==undefined){
        console.log("暂无图片");
        return;
    }
    console.log("遍历图片数量:"+imgs.length);
    if(imgs.length == 0){
        console.log();
        return;
    }
    for(var i=0;i<imgs.length;i++){
        var item = imgs[i];
        item.onerror = imgLoadError;
    }
}

//加载图片回调
function imgLoadError(){
    console.log('错误回调成功');
    var tagImg = event.srcElement;
    tagImg.src = 'error.png';
}

//滑动显示图片
RE.scrollLoadItemImg = function(scrollTop,screenHeight){
    console.log("滑动:"+scrollTop+"  "+screenHeight);
    var imgs = document.getElementsByTagName("img");
    if(imgs==undefined){
        console.log("暂无图片");
        return;
    }
    console.log("遍历图片数量:"+imgs.length);
    if(imgs.length == 0){
        console.log();
        return;
    }
    for(var i=0;i<imgs.length;i++){
        var item = imgs[i];
        var top = item.offsetTop;
        var left = item.offsetLeft;

        console.log("遍历:"+i)
        if(window.app!=undefined){
            var tagUrl = parseQueryString(item.src);
            if(tagUrl==undefined){
                window.app.log("图片已经加载了:"+i);
            }else{
                //判断位置
                var startY = scrollTop;
                var endY = scrollTop + screenHeight/2;
                window.app.log(startY+"  "+endY+"  "+top);
                if(startY <= top && top <= endY){
                    item.src = tagUrl;
                    window.app.log(i+"位置加载图片:"+top+"  "+i);
                    //一次加载多张图片
                }else{
                    //window.app.log(i+"位置不加图片:"+top+"  "+i);
                    //停止遍历后面的
                    continue;
                }
                
            }
        }
    }
}

RE.scrollLoadImg = function(){
    var imgs = document.getElementsByTagName("img");
    if(imgs==undefined){
        console.log("暂无图片");
        return;
    }
    console.log("遍历图片数量:"+imgs.length);
    if(imgs.length == 0){
        console.log();
        return;
    }
    for(var i=0;i<imgs.length;i++){
        var item = imgs[i];
        var top = item.offsetTop;
        var left = item.offsetLeft;
        if(window.app!=undefined){
            var tagUrl = parseQueryString(item.src);
            if(tagUrl==undefined){
                window.app.log("图片已经加载了:"+item.src);
            }else{
                window.app.log(i+"位置:"+top+"  "+left+" "+tagUrl);
                item.src = tagUrl;
            }
        }
    }
}

function parseQueryString(url){
    //用来匹配的key
    var key = "default_logo.png?mddImageUrl=";
    var start = url.indexOf(key);
    if(start<0){
        console.log("匹配失败")
        return undefined;
    }
    var str = url.substr(start+key.length);
    console.log(str);
    return str;
}

//和图片懒加载冲突
//解决方案 如果有视频标签，就不进行懒加载
//添加视频开始播放回调
RE.addVideoPlayListener = function(){
    var arr = document.getElementsByTagName("video");
    if(arr==undefined || arr.length == 0){
        return;
    }
    for(var i=0;i<arr.length;i++){
        arr[0].addEventListener("canplay",canPlayListener);
    }
}

//video播放回调
function canPlayListener(){
    //SomeJavaScriptCode
    if(window.app==undefined)return;
    console.log("视频开始播放了");
    window.app.videoStartPlayCallBack();
}