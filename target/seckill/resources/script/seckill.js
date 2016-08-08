var seckill = {
    //Pack ajax related url
    URL: {},
    validateMobile: function (mobile) {
        if (mobile && mobile.length == 11 && isNaN(mobile)) {
            return true;
        } else {
            return false;
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
        }
    }
}