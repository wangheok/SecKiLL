<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>SecKill Detail Page</title>
    <%@include file="common/head.jsp" %>
</head>
<body>
<div class="container">
    <div class="panel panel-default text-center">
        <div class="panel-heading">
            <h1>${secKill.name}</h1>
        </div>
        <div class="panel-body">
            <h2 class="text-danger">
                <span class="glyphicon glyphicon-time"></span>
                <span class="glyphicon" id="seckill-box"></span>
            </h2>
        </div>
    </div>
    <div id="killMobileModal" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title text-center">
                        <span class="glyphicon glyphicon-phone"></span>
                    </h3>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-xs-8 col-xs-offset-2">
                            <input type="text" name="killMobile" id="killMobileKey" placeholder="Enter mobile No."
                                   class="form-control">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <span id="killMobileMessage" class="glyphicon"></span>
                    <button type="button" id="killMobileBtn" class="btn btn-success">
                        <span class="glyphicon glyphicon-phone"></span>
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<!-- Import jQuery before importing bootstrap.min.js -->
<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
<!-- Latest Bootstrap core JavaScript file -->
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>

<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<script src="http://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.min.js"></script>
<%--Interaction Logic--%>
<script src="/resources/script/seckill.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        // pass params by EL
        seckill.detail.init({
            seckillId: ${secKill.secKillId},
            startTime: ${secKill.startTime.time},
            endTime: ${secKill.endTime.time}
        });
    });
</script>
</html>
