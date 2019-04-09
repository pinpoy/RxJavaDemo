(function (count, _undefined) {
    var global = this;
    if (function (t) {
            Array.prototype.forEach || (Array.prototype.forEach = function (e) {
                for (var o = 0, n = this.length; o < n; o += 1) e.call(t, this[o], o, this)
            })
        }(this), function (t, e) {
            var o = window.xjsObj;
            globalStore = {};
            var n, a = t.native = {
                    _unique: "me"
                },
                i = t.util;
            a._invoke = function (t, e) {
                var n, a = e ? JSON.stringify(e) : "{}";
                try {
                    return (n = o[t] && o[t](a)) && i.parseJSON(n) || {}
                } catch (e) {
                    console.log(t, e, o);
                }
            }
        }(this), !window.xjsObj);
    util = global.util, native = global.native;

    function gamble(percent) {
        return Math.random() * 100 < percent;
    };

    function randomInt(t) {
        return Math.floor(t * Math.random())
    }

    function randomRange(n, m) {
        var random = Math.floor(Math.random() * (m - n + 1) + n);
        return random;
    }

    function playVideo() {
        var video = document.querySelector("video");
        video.play();
        video.error
    }

    function pauseVideo() {
        var video = document.querySelector("video");
        video.pause();
    }

    function setVideoProgress(progress) {
        var video = document.querySelector("video");
        video.currentTime = getVideoTimeLenght() * progress / 100;
    }

    function getVideoTimeLenght() {
        var video = document.querySelector("video");
        return video.duration;
    }

    function silentVideo() {
        var video = document.querySelector("video");
        video.volume = 0;
    }

    function preloadVideo() {
        var video = document.querySelector("video");
        video.preload = "auto";
    }

    function enterVideo() {
        var paras = document.getElementsByClassName("img video-img fixed-ratio-size size-16-9");
        var lengh = paras.length;
        if (lengh == 0) {
            scrollBottom();
        } else {
            var choose = randomRange(0, lengh - 1);
            paras[choose].click();
        }

    }

    function endListener() {
        var aud = document.querySelector("video");
        aud.onended = function () {
            console.log("end");
            native._invoke("end","the whole end")
        }
    }


    function setVideoPlayRate(rate) {
        var video = document.querySelector("video");
        video.defaultPlaybackRate = rate;
    }

    function scrollFree() {
        x = randomInt(document.body.scrollWidth);
        y = randomInt(document.body.scrollHeight);
        scrollTo(x, y)
    }

    function scrollBottom() {
        x = 0;
        y = document.body.scrollHeight + 10;
        scrollTo(x, y)
    }

    function scrollPull() {
        x = randomInt(document.body.scrollWidth);
        y = -10;
        scrollTo(x, y)
    }

    function goFree() {
        window.location.href = "www.baidu.com";
    }

    var length = 0,
        current = 0;

    function moveAction() {
        try {
            if (length == 0)
                length = getVideoTimeLenght();
            current = document.querySelector("video").currentTime;
            console.log(" " + length + " " + current);

            if (length - current < randomRange(5, 15)) {
                native._invoke("end", "end"), goFree();
            } else if (gamble(randomRange(8, 10))) {
                var next = (length - current) / randomRange(10, 30) + current;
                if (next >= lengh - 5) {
                    setVideoProgress(95);
                } else
                    setVideoProgress(next / length * 100);
            }
        } catch (error) {
            console.log(error);
        }


    }

    if (location.href.indexOf("cpu.baidu.com") != -1 && location.href.indexOf("video") == -1) {
        scrollBottom();
        enterVideo();
    } else if (location.href.indexOf("cpu.baidu.com") != -1 && location.href.indexOf("video") != -1) {
        preloadVideo();
        silentVideo();
        playVideo();
        endListener();

        var delayTime = 10000;
        setInterval(function () {
            delayTime = 5000;
            native._invoke("jsRun", "jsRun"),
                moveAction();
        }, delayTime);
        setTimeout(function () {
            native._invoke("end", "end"), goFree();
        }, 1000 * 60 * 4);
    }

}).call(window);