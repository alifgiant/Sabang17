/**
 * Sabang Sigle Page App
 */

// Firebase imported in html
// JQuery imported in html

function loadMenu(menu, child_menu, element_menu) {
    if (!child_menu) {
        var submenus = $('.sub-menu');
        submenus.removeClass("active");
        submenus.find('a').removeClass("active");

        $(element_menu).parent().addClass("active");
        $(element_menu).addClass("active");
    }

    var main_content_holder = $('#main-content');

    console.log(menu, child_menu);
    switch (menu){
        case 'dashboard':
            main_content_holder.load('dashboard.html');
            break;
        case 'event':
            if (child_menu === 'add')
                main_content_holder.load('event/add.html');
            else if (child_menu === 'detail')
                main_content_holder.load('event/detail.html');
            break;
        case 'cadre':
            if (child_menu === 'add')
                main_content_holder.load('event/add.html');
            else if (child_menu === 'detail')
                main_content_holder.load('event/detail.html');
            break;
        case 'opinion':
            if (child_menu === 'add')
                main_content_holder.load('event/add.html');
            else if (child_menu === 'detail')
                main_content_holder.load('event/detail.html');
            break;
        case 'business':
            if (child_menu === 'add')
                main_content_holder.load('event/add.html');
            else if (child_menu === 'detail')
                main_content_holder.load('event/detail.html');
            break;
        case 'aspiration':
            main_content_holder.load('aspiration.html');
        case 'credit':
            main_content_holder.load('credits.html');
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
            $('#main-content').load('dashboard.html');
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