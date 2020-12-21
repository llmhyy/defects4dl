$(document).ready(function () {
    // set editor content
    $('#mergely').mergely({
        cmsettings :{

        },

        lhs: function(setValue) {
            setValue('the quick red fox\njumped over the hairy dog');
        },
        rhs: function(setValue) {
            setValue('the quick brown fox\njumped over the lazy dog');
        }
    });
});