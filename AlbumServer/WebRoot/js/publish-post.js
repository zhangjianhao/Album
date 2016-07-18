/**
 * Created by zjianhao on 16-3-26.
 */

function onselected(obj){

    var mainForum = document.getElementById("mainforum");
    console.log("size:"+mainForum.length);
    for (var i =0; i<mainForum.length; i++){
        console.log("main:"+mainForum[i].value);
        var sub = document.getElementById(mainForum[i].value);
        sub.style.display = "none";
        sub.name="sub"
        console.log("sub value:"+sub.value);
    }
    var value = obj.value;
    console.log("select value:"+value);
    var subForum = document.getElementById(value);
    subForum.style.display = "";
    subForum.name = "subForum"

}