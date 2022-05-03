[Chinese version / 中文介绍](README_CN.md)

# HSPD--CN  
>Warehouse Overview  
This is the Chinese dungeon build version of Difficult Germination, initiated by JDSALing!  

>Partners  
Co-coders: Alice, Mason369  

Translation reviewers: Rowberry, Alice, Mason369, JDSALing  

>Translators  
-To be determined  

>Why sinicize it?  
Dedicated to Difficult Germination author SmuJB and the majority of players  

>Total progress:  
Multilingual underlying architecture  

<!DOCTYPE html>
<html>
<head>
  <title>'</title>
</head>
<style type="text/css">
  #sub{
    animation: removef 2s ease-in;
    -webkit-animation:removef 2s ease-in;
    background: #aacc33;
    height: 100%;
    width: 80%;
  }
  @keyframes removef{
    0% {width: 0%;}
    100% {width: 80%;}

  }
  @-webkit-keyframes removef{
     0% {width: 0%;}
     100% {width: 80%;}
  }
  #progress{
  width: 50%;
  height: 30px;
  border:1px solid #ccc;
  border-radius: 15px;

  overflow: hidden;
  box-shadow: 0 0 5px 0px #ddd inset;
}
#progress span {
  display: inline-block;
  /*进度*/
  width: 10%;
  height: 100%;
  background: #009900;
  color:#fff;
  -webkit-animation:load 3s ease-in;
}
@-webkit-keyframes load{
  0%{
    width: 0%;
  }
  100%{
    width:10%;
  }
}

.circleProgress_wrapper{
  width: 200px;
  height: 200px;
  margin: 50px auto;
  position: relative;
  border:1px solid #ddd;
}
.wrapper{
  width: 100px;
  height: 200px;
  position: absolute;
  top:0;
  overflow: hidden;
}
.right{
  right:0;
}
.left{
  left:0;
}
.circleProgress{
  width: 160px;
  height: 160px;
  border:20px solid rgb(232, 232, 12);
  border-radius: 50%;
  position: absolute;
  top:0;
  -webkit-transform: rotate(45deg);
}
.rightcircle{
  border-top:20px solid green;
  border-right:20px solid green;
  right:0;
  -webkit-animation: circleProgressLoad_right 5s linear infinite;
}
.leftcircle{
  border-bottom:20px solid green;
  border-left:20px solid green;
  left:0;
  -webkit-animation: circleProgressLoad_left 5s linear infinite;
}
@-webkit-keyframes circleProgressLoad_right{
  0%{
    border-top:20px solid #ED1A1A;
    border-right:20px solid #ED1A1A;
    -webkit-transform: rotate(45deg);
  }
  50%{
    border-top:20px solid rgb(232, 232, 12);
    border-right:20px solid rgb(232, 232, 12);
    border-left:20px solid rgb(81, 197, 81);
    border-bottom:20px solid rgb(81, 197, 81);
    -webkit-transform: rotate(225deg);
  }
  100%{
    border-left:20px solid green;
    border-bottom:20px solid green;
    -webkit-transform: rotate(225deg);
  }
}
@-webkit-keyframes circleProgressLoad_left{
  0%{
    border-bottom:20px solid #ED1A1A;
    border-left:20px solid #ED1A1A;
    -webkit-transform: rotate(45deg);
  }
  50%{
    border-bottom:20px solid rgb(232, 232, 12);
    border-left:20px solid rgb(232, 232, 12);
    border-top:20px solid rgb(81, 197, 81);
    border-right:20px solid rgb(81, 197, 81);
    -webkit-transform: rotate(45deg);
  }
  100%{
    border-top:20px solid green;
    border-right:20px solid green;
    border-bottom:20px solid green;
    border-left:20px solid green;
    -webkit-transform: rotate(225deg);
  }
}
</style>
<body>
开发总进度：<br>
Dev Progess:
<div id="progress">
  <span></span>
</div>
</body>
</html>
