var seckill = {
    //Pack ajax related url
    URL: {
        now: function () {
            return '/seckill/time/now';
        },
        exposer: function (secKillId) {
            return '/seckill/' + secKillId + '/exposer';
        },
        execution: function (secKillId, md5) {
            return '/seckill/' + secKillId + '/' + md5 + '/execution';
        }
    },
    validateMobile: function (mobile) {
        if (mobile && mobile.length == 11 && !isNaN(mobile)) {
            return true;
        } else {
            return false;
        }
    },
    // Get seckill address, control business logic, seckill execution
    killHandler: function (secKillId, node) {
        // Handle seckill logic
        node.hide()
            .html('<button class="btn btn-primary btn-lg" id="killBtn">Start</button>');
        $.post(seckill.URL.exposer(secKillId), {}, function (result) {
            // executing interaction process in callback method
            if (result && result['success']) {
                var exposer = result['data'];
                if (exposer['exposed']) {
                    // if started
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(secKillId, md5);
                    console.log('Kill Url: ' + killUrl);
                    // .one() -> bundle only once click event in case of constant clicking,
                    // too much pressure for server
                    $('#killBtn').one('click', function () {
                        // Execute seckill request
                        // 1. Disable button
                        $(this).addClass('disabled');
                        // 2. Send seckill request
                        $.post(killUrl, {}, function (result) {

                            if (result && result['success']) {
                                var secKillResult = result['data'];
                                var state = secKillResult['state'];
                                var stateInfo = secKillResult['stateInfo'];
                                // Display purchase result
                                if (state == 1) {
                                    node.html('<span class="label label-success">' + stateInfo + '</span>');
                                } else {
                                    node.html('<span class="label label-danger">' + stateInfo + '</span>');
                                }
                            }
                        });
                    });
                    node.show();// seckill-box node

                } else {
                    // Not started yet
                    var now = exposer['currentSysTime'];
                    var start = exposer['startTime'];
                    var end = exposer['endTime'];
                    // count time over again because of declination
                    seckill.countdown(secKillId, now, start, end);
                }
            } else {
                console.log('result: ' + result);
            }
        });
    },
    countdown: function (secKillId, currentTime, startTime, endTime) {

        var secKillBox = $('#seckill-box');

        if (currentTime > endTime) {
            // secKill has been closed
            secKillBox.html('This seckill has been closed!');
        } else if (currentTime < startTime) {
            // This seckill is not started yet!
            var killTime = new Date(startTime + 1000);

            secKillBox.countdown(killTime, function (event) {
                // Controlling time format
                var format = event.strftime('Countdown: %D天 %H时 %M分 %S秒');
                secKillBox.html(format);
                //    callback event after count to zero
            }).on('finish.countdown', function () {
                // Get seckill address, control business logic, seckill execution
                seckill.killHandler(secKillId, secKillBox);
            });
        } else {
            // Seckill start
            seckill.killHandler(secKillId, secKillBox);
        }
    },
    //Detail page logic
    detail: {
        // Detail page init
        init: function (params) {
            // Mobile number verification, countdown interaction
            var killMobile = $.cookie('killMobile');
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            //    Mobile No. Verification
            if (!seckill.validateMobile(killMobile)) {
                // Bind mobile No.
                var killMobileModal = $('#killMobileModal');
                // Display pop-up
                killMobileModal.modal({
                    show: true,
                    backdrop: 'static',
                    keyboard: false // Disable keyboard event
                });

                $('#killMobileBtn').click(function () {

                    var inputMobile = $('#killMobileKey').val();
                    if (seckill.validateMobile(inputMobile)) {

                        $.cookie('killMobile', inputMobile, {expires: 7, path: '/seckill'});
                        // Refresh page
                        window.location.reload();
                    } else {
                        $('#killMobileMessage').hide().html('<label class="label label-danger">Incorrect mobile No.</label>').show(300);
                    }
                });
            }
            //  Login in already
            // Countdown interaction
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            $.get(seckill.URL.now(), {}, function (result) {
                // Get system time
                if (result && result['success']) {
                    var nowTime = result['data'];
                    // Time verification
                    seckill.countdown(seckillId, nowTime, startTime, endTime);
                } else {
                    console.log('result' + result);
                }
            });
        }
    }
}