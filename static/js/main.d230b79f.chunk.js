(this.webpackJsonpclient=this.webpackJsonpclient||[]).push([[0],{51:function(e,n,t){},52:function(e,n,t){},83:function(e,n){},86:function(e,n,t){},87:function(e,n,t){},88:function(e,n,t){},95:function(e,n,t){"use strict";t.r(n);var c=t(0),r=t(43),a=t.n(r),o=(t(51),t(45)),s=t(2),i=t(12),u=t(97),l=(t(52),t(1)),d=function(e){var n=Object(c.useState)(""),t=Object(i.a)(n,2),r=t[0],a=t[1];return Object(l.jsx)("div",{className:"btn-page",children:Object(l.jsxs)("div",{className:"btn-container",children:[Object(l.jsx)("button",{className:"create-btn",onClick:function(){var n=Object(u.a)();e.history.push("/room/".concat(n))},children:"Create Room"}),Object(l.jsx)("input",{type:"text",placeholder:"Input chat ID here",value:r,onChange:function(e){a(e.target.value)}}),Object(l.jsx)("button",{className:"create-btn",onClick:function(){e.history.push("/room/".concat(r))},children:"Join"})]})})},m=t(26),j=t(44),h=t.n(j),f=(t(86),function(e){var n=e.messages;return Object(l.jsx)("div",{className:"messages_container",children:n.map((function(e,n){return e.yours?Object(l.jsx)("div",{className:"my_message_container",children:Object(l.jsx)("div",{className:"my_message",children:e.value})},n):Object(l.jsxs)("div",{className:"received_message_container",children:[Object(l.jsx)("div",{className:"received_message",children:e.value}),Object(l.jsx)("div",{id:"usr-r",children:"Username"})]},n)}))})}),b=(t(87),function(e){var n=Object(c.useRef)(),t=Object(c.useRef)(),r=Object(c.useRef)(),a=Object(c.useRef)(),o=Object(c.useState)(""),s=Object(i.a)(o,2),u=s[0],d=s[1],j=Object(c.useState)([]),b=Object(i.a)(j,2),p=b[0],v=b[1];function O(e){v((function(n){return[].concat(Object(m.a)(n),[{yours:!1,value:e.data}])}))}function g(e){var c=new RTCPeerConnection({iceServers:[{urls:"stun:nettverksprogg.eastus2.cloudapp.azure.com:3478"}]});return c.onicecandidate=w,c.onnegotiationneeded=function(){return function(e){n.current.createOffer().then((function(e){return n.current.setLocalDescription(e)})).then((function(){var c={target:e,caller:t.current.id,sdp:n.current.localDescription};t.current.emit("offer",c)})).catch((function(e){return console.log(e)}))}(e)},c}function x(e){n.current=g(),n.current.ondatachannel=function(e){a.current=e.channel,a.current.onmessage=O};var c=new RTCSessionDescription(e.sdp);n.current.setRemoteDescription(c).then((function(){})).then((function(){return n.current.createAnswer()})).then((function(e){return n.current.setLocalDescription(e)})).then((function(){var c={target:e.caller,caller:t.current.id,sdp:n.current.localDescription};t.current.emit("answer",c)}))}function N(e){var t=new RTCSessionDescription(e.sdp);n.current.setRemoteDescription(t).catch((function(e){return console.log(e)}))}function w(e){if(e.candidate){var n={target:r.current,candidate:e.candidate};t.current.emit("ice-candidate",n)}}function C(e){var t=new RTCIceCandidate(e);n.current.addIceCandidate(t).catch((function(e){return console.log(e)}))}function D(){a.current.send(u),v((function(e){return[].concat(Object(m.a)(e),[{yours:!0,value:u}])})),d("")}Object(c.useEffect)((function(){t.current=h.a.connect("https://signaling-server-nettprogg.herokuapp.com/"),t.current.emit("join room",e.match.params.roomID),t.current.on("other user",(function(e){!function(e){n.current=g(e),a.current=n.current.createDataChannel("sendChannel"),a.current.onmessage=O}(e),r.current=e})),t.current.on("user joined",(function(e){r.current=e})),t.current.on("offer",x),t.current.on("answer",N),t.current.on("ice-candidate",C)}),[]);return Object(l.jsxs)("div",{className:"container",children:[Object(l.jsxs)("h1",{children:["Chat room: ",e.match.params.roomID]}),Object(l.jsxs)("div",{className:"username",children:[Object(l.jsx)("input",{id:"name",type:"text",placeholder:"Username"}),Object(l.jsx)("button",{id:"name-btn",children:"Submit"})]}),Object(l.jsx)(f,{messages:p}),Object(l.jsxs)("div",{className:"send-msg",children:[Object(l.jsx)("textarea",{className:"msg-box",value:u,onChange:function(e){d(e.target.value)},onKeyDown:function(e){"Enter"===e.key&&D()},placeholder:"Write your message here..."}),Object(l.jsx)("button",{className:"btn",onClick:D,children:"Send"})]})]})});t(88);var p=function(){return Object(l.jsx)("div",{className:"App",children:Object(l.jsx)(o.a,{basename:"/STUN-server---IDATT2104-Nettverksprogrammering",children:Object(l.jsxs)(s.c,{children:[Object(l.jsx)(s.a,{path:"/",exact:!0,component:d}),Object(l.jsx)(s.a,{path:"/room/:roomID",component:b})]})})})};Boolean("localhost"===window.location.hostname||"[::1]"===window.location.hostname||window.location.hostname.match(/^127(?:\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}$/));a.a.render(Object(l.jsx)(p,{}),document.getElementById("root")),"serviceWorker"in navigator&&navigator.serviceWorker.ready.then((function(e){e.unregister()})).catch((function(e){console.error(e.message)}))}},[[95,1,2]]]);
//# sourceMappingURL=main.d230b79f.chunk.js.map