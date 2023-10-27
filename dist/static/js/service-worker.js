!function(e) {
    var t = {};
    function n(i) {
        if (t[i])
            return t[i].exports;
        var r = t[i] = {
            i: i,
            l: !1,
            exports: {}
        };
        return e[i].call(r.exports, r, r.exports, n),
        r.l = !0,
        r.exports
    }
    n.m = e,
    n.c = t,
    n.d = function(e, t, i) {
        n.o(e, t) || Object.defineProperty(e, t, {
            enumerable: !0,
            get: i
        })
    }
    ,
    n.r = function(e) {
        "undefined" != typeof Symbol && Symbol.toStringTag && Object.defineProperty(e, Symbol.toStringTag, {
            value: "Module"
        }),
        Object.defineProperty(e, "__esModule", {
            value: !0
        })
    }
    ,
    n.t = function(e, t) {
        if (1 & t && (e = n(e)),
        8 & t)
            return e;
        if (4 & t && "object" == typeof e && e && e.__esModule)
            return e;
        var i = Object.create(null);
        if (n.r(i),
        Object.defineProperty(i, "default", {
            enumerable: !0,
            value: e
        }),
        2 & t && "string" != typeof e)
            for (var r in e)
                n.d(i, r, function(t) {
                    return e[t]
                }
                .bind(null, r));
        return i
    }
    ,
    n.n = function(e) {
        var t = e && e.__esModule ? function() {
            return e.default
        }
        : function() {
            return e
        }
        ;
        return n.d(t, "a", t),
        t
    }
    ,
    n.o = function(e, t) {
        return Object.prototype.hasOwnProperty.call(e, t)
    }
    ,
    n.p = "/",
    n(n.s = 2)
}({
    2: function(e, t, n) {
        e.exports = n("S1BP")
    },
    S1BP: function(e, t, n) {
        "use strict";
        var i = {
            version: "achilles",
            staticCacheItems: ["/templates/assets/css/app.css", "/templates/assets/json/manifest.json", "/offline"],
            offlineImage: '<svg role="img" aria-labelledby="offline-title" viewBox="0 0 400 300" xmlns="http://www.w3.org/2000/svg"><title id="offline-title">Offline</title><g fill="none" fill-rule="evenodd"><path fill="#D8D8D8" d="M0 0h400v300H0z"/><text fill="#9B9B9B" font-family="Times New Roman,Times,serif" font-size="72" font-weight="bold"><tspan x="93" y="172">offline</tspan></text></g></svg>',
            offlinePage: "/offline"
        };
        function r(e, t, n) {
            if (n.ok) {
                var i = n.clone();
                caches.open(e).then((function(e) {
                    e.put(t, i)
                }
                ))
            }
            return n
        }
        function o(e, t) {
            return "image" === e ? new Response(t.offlineImage,{
                headers: {
                    "Content-Type": "image/svg+xml"
                }
            }) : "content" === e ? caches.match(t.offlinePage) : void 0
        }
        function c(e) {
            return caches.match(e.request).then((function(t) {
                if (!t)
                    throw Error("".concat(e.request.url, " not found in cache"));
                return t
            }
            ))
        }
        addEventListener("install", (function(e) {
            e.waitUntil(caches.open("static-v8").then((function(e) {
                e.addAll(i.staticCacheItems)
            }
            )))
        }
        )),
        addEventListener("activate", (function(e) {
            e.waitUntil(caches.keys().then((function(e) {
                return Promise.all(e.map((function(e) {
                    if ("static-v8" != e)
                        return caches.delete(e)
                }
                )))
            }
            )).then((function() {
                console.log("New version now ready to handle fetches!")
            }
            )))
        }
        )),
        addEventListener("fetch", (function(e) {
            (function(e, t) {
                var n = e.request
                  , i = new URL(n.url)
                  , r = {
                    isGETRequest: "GET" === n.method,
                    isFromMyOrigin: i.origin === self.location.origin
                };
                return !Object.keys(r).filter((function(e) {
                    return !r[e]
                }
                )).length
            }
            )(e) && function(e, t) {
                var n, i = e.request, f = i.headers.get("Accept"), u = "static";
                null != f ? -1 !== f.indexOf("text/html") ? u = "content" : -1 !== f.indexOf("image") && (u = "image") : u = "content",
                n = u,
                "content" === u ? e.respondWith(fetch(i).then((function(e) {
                    return r(n, i, e)
                }
                )).catch((function() {
                    return c(e)
                }
                )).catch((function() {
                    return o(u, t)
                }
                ))) : e.respondWith(c(e).catch((function() {
                    return fetch(i)
                }
                )).then((function(e) {
                    return r(n, i, e)
                }
                )).catch((function() {
                    return o(u, t)
                }
                )))
            }(e, i)
        }
        ))
    }
});
