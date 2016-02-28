<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>Railway interlocking Online</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">

    <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="bootstrap/css/docs.min.css">
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript">
        function jsonAjaxPost() {
            var userNameObj = $("#username").val();
            var contentObj = $("#content").val();
            $.ajax({
                type: "post",//请求方式
                url: "main?username=" + encodeURI(encodeURI(userNameObj))
                + "&content=" + encodeURI(encodeURI(contentObj)),//发送请求地址
                timeout: 30000,//超时时间：30秒
                dataType: "json",//设置返回数据的格式
                //请求成功后的回调函数 data为json格式
                success: function (data) {
                    alert(data);
                    $("#resultJsonText").text("你的名字：" + data.username + "  你输入的内容:" + data.content);
                },
                //请求出错的处理
                error: function () {
                    alert("请求出错");
                }
            });
        }
    </script>
</head>
<body>

<script>
    function dire() {
        var val = $('#direction').val();
        if (val == 0) {
            $('#downRoutes').css('display', 'block');
            $('#upRoutes').css('display', 'none');
        } else {
            $('#upRoutes').css('display', 'block');
            $('#downRoutes').css('display', 'none');
        }
    }

    function addTrain() {
        var val = $('#direction').val();
        var source = "";
        var dest = "";
        var passby = "";
        var dire = "";
        if (val == 0) {
            dire = "down";
        } else {
            dire = "up";
        }
        source = $("#" + dire + "Source").text();
        dest = $("#" + dire + "Dest").text();
        passby = source + ";" + $("#" + dire + "Mid").val() + ";" + dest;

        $("#trainTable").append('<tr class="danger">' +
                '<th scope="row">10</th>' +
                '<td>' + source + '</td>' +
                '<td>' + dest + '</td>' +
                '<td>' + passby + '</td>' +
                '<td>' + "noidea" + '</td>' +
                '</tr>');

    }
</script>

<div class="bs-docs-header"  tabindex="-1">
    <div class="container">
        <h1>Railway interlocking system</h1>

        <p>Simple is beautiful.</p>

        <div id="carbonads-container">
            <div class="carbonad">
                <div id="azcarbon"></div>
                <script>var z = document.createElement("script");
                z.async = !0, z.src = "http://engine.carbonads.com/z/32341/azcarbon_2_1_0_HORIZ";
                var s = document.getElementsByTagName("script")[0];
                s.parentNode.insertBefore(z, s);</script>
            </div>
        </div>
    </div>
</div>

<div id="mainbody" class="container" data-example-id="basic-forms">
    <form id="form">
        <div style="padding-bottom: 20px">
            <select id="direction" class="form-control input-lg" onchange="dire()">
                <option value="0">down</option>
                <option value="1">up</option>
            </select>
        </div>

        <div class="bs-example" style="text-align: center">
        <div id="upRoutes" class="row" style="display: none;padding-bottom: 20px">
            <div class="col-md-2">UP direction</div>
            <div class="col-md-2" id="upSource">s1</div>
            <div class="col-md-2">
                <select class="form-control input-lg" id="upMid">
                    <option value="s4">s4</option>
                    <option value="s6">s6</option>
                </select>
            </div>
            <div class="col-md-2" id="upDest">s7</div>
            <div class="col-md-2">
                <button type="button" class="btn btn-default" onclick="addTrain()">Submit</button>
            </div>
        </div>

        <div id="downRoutes" class="row" style="display: block;padding-bottom: 20px">
            <div class="col-md-2">DOWN direction</div>
            <div class="col-md-2" id="downSource">s8</div>
            <div class="col-md-2">
                <select class="form-control input-lg" id="downMid">
                    <option value="s3">s3</option>
                    <option value="s5">s5</option>
                </select>
            </div>
            <div class="col-md-2" id="downDest">s2</div>
            <div class="col-md-2">
                <button type="button" class="btn btn-default" onclick="addTrain()">Submit</button>
            </div>
        </div>
        </div>

    </form>

    <div class="bs-example" data-example-id="contextual-table">
        <table class="table" id="trainTable">
            <thead>
            <tr>
                <th>Index</th>
                <th>Source</th>
                <th>Dest</th>
                <th>Pass-by</th>
                <th>State</th>
            </tr>
            </thead>
            <tbody>
            <tr class="active">
                <th scope="row">1</th>
                <td>Column content</td>
                <td>Column content</td>
                <td>Column content</td>
                <td>Column content</td>
            </tr>
            <tr class="success">
                <th scope="row">3</th>
                <td>Column content</td>
                <td>Column content</td>
                <td>Column content</td>
                <td>Column content</td>
            </tr>
            <tr class="info">
                <th scope="row">5</th>
                <td>Column content</td>
                <td>Column content</td>
                <td>Column content</td>
                <td>Column content</td>
            </tr>
            <tr class="warning">
                <th scope="row">7</th>
                <td>Column content</td>
                <td>Column content</td>
                <td>Column content</td>
                <td>Column content</td>
            </tr>
            <tr class="danger">
                <th scope="row">9</th>
                <td>Column content</td>
                <td>Column content</td>
                <td>Column content</td>
                <td>Column content</td>
            </tr>
            </tbody>
        </table>
    </div>

    <div id="railway" class="bs-example" >

        <script >
            window.onload = function(){
                var canvas = document.getElementById("myCanvas");
                var context = canvas.getContext("2d");
                //横线与竖线的间隔距离
                var dx = 30;
                var dy = 30;
                //初始坐标点
                var x=0;
                var y=0;
                var w=canvas.width;
                var h=canvas.height;
                //单个步长所表示的长度
                var xy=10;
                //画横线
                while(y<h){
                    y=y+dy;
                    context.moveTo(x, y);
                    context.lineTo(w,y);
                    context.stroke();
                    //横坐标的数字
                    context.font = "1pt Calibri";
                    context.fillText(xy, x, y);
                    xy+=10;
                }
                //画竖线
                y=0;
                xy=10;
                while(x<w){
                    x=x+dx;
                    context.moveTo(x, y);
                    context.lineTo(x,h);
                    context.stroke();
                    //纵坐标的数字
                    context.font = "1pt Calibri";
                    context.fillText(xy,x,10);
                    xy+=10;
                }

            };
        </script>
        <canvas id="myCanvas" width="1200" height="1200">
        </canvas>
    </div>

</div>

<form id="form1" method="post">
    <p>
        评论：
    </p>

    <p>
        姓名：
        <input type="text" name="username" id="username"/>
    </p>

    <p>
        内容：
        <textarea name="content" id="content" rows="2" cols="20"></textarea>
    </p>

    <p>
        <input type="button" id="send" value="提交" onclick="jsonAjaxPost()"/>
    </p>
</form>
<div class="comment">
    返回数据：
    <p id="resultJsonText"></p>
</div>
<div id="resText">
</div>
</body>
</html>  