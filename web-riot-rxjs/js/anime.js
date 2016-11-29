
var animeUpdate = function(wd) {
    return function() {
        var el = document.getElementById('w-' + wd.id);
        wd.Ypos = el.getBoundingClientRect().top / window.innerHeight * 100;
    };
};

var animeDone = function(me, wd) {
    return function() {
        var idx = me.words.findIndex(function(w) {
            return wd === w;
        });
        if (idx != -1) {
            me.words.splice(idx, 1);
        }
        me.update({words:me.words});
    };
};

var startAnime = function(me, wd) {
    var goal = window.innerHeight * 0.90;
    anime({
        targets: [document.getElementById('w-' + wd.id)],
        translateY: [0, goal], duration: 8000,
        update: animeUpdate(wd), complete:animeDone(me,wd)
    });
}

module.exports = startAnime;
