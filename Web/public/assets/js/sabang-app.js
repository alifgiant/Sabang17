/**
 * Sabang Sigle Page App
 */

// Firebase imported in html
// JQuery imported in html

function loadMenu(menu, child_menu, element_menu) {
    if (!child_menu) {
        $('.sub-menu').removeClass("active");
        $('.sub-menu').find('a').removeClass("active");

        $(element_menu).parent().addClass("active");
        $(element_menu).addClass("active");
    }

    console.log(menu, child_menu);
    switch (menu){
        case 'dashboard': break;
        case 'event': break;
        case 'cadre': break;
        case 'opinion': break;
        case 'business': break;
        case 'aspiration': break;
    }
}

function onAuthChange(user) {
    if (user) {
        $('#container').load('navigations.html', function () {
            var email = user.email.split('@');
            $('#username').text(email[0].toUpperCase());
            if (user.photoURL){
                $('#user_image').attr("src", user.photoURL);
            }
        });
        $('.backstretch').remove();
    } else {
        // User is not signed in.
        $('#container').load('login.html');
        $.backstretch("assets/img/login-bg.jpg", {speed: 500});
    }
    // ...
}

function init() {
    // Initialize Firebase
    var config = {
        apiKey: "AIzaSyC_7wS9coUFyGYErrLjziiTYXJrN5IfMd4",
        authDomain: "sabang-17.firebaseapp.com",
        databaseURL: "https://sabang-17.firebaseio.com",
        projectId: "sabang-17",
        storageBucket: "sabang-17.appspot.com",
        messagingSenderId: "307103939210"
    };
    firebase.initializeApp(config);
    firebase.auth().onAuthStateChanged(onAuthChange);

    var user = firebase.auth().currentUser;
    onAuthChange(user);
}


// start app
init();