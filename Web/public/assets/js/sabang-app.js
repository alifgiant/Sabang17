/**
 * Sabang Sigle Page App
 */

// Firebase imported in html
// JQuery imported in html

function onDashBoardLoad(){
    
}

function onDetailEventLoad(){
    
}

function onDetailCadreLoad(){
    
}

function onDetailOpinionLoad(){
    
}

function onDetailBusinessLoad(){
    
}

function onDetailAspirationLoad(){
    
}

function hideOther(){
    var main_content_holder = $('#main-content');
    main_content_holder.find('.wrapper').addClass('hidden');
}

function loadMenu(menu, child_menu, element_menu) {
    if (!child_menu) {
        var submenus = $('.sub-menu');
        submenus.removeClass("active");
        submenus.find('a').removeClass("active");

        $(element_menu).parent().addClass("active");
        $(element_menu).addClass("active");
    }

    var main_content_holder = $('#main-content');
    switch (menu){
        case 'dashboard':
            hideOther();
            var dashboard = $('#dashboard');
            if (!dashboard.length){
                $.get('dashboard.html', function(data){
                    main_content_holder.append(data);
                    onDashBoardLoad();
                });
            }else{
                dashboard.removeClass('hidden');
            }
            break;
        case 'event':
            if (child_menu === 'add'){
                var add = $('#event_add');
                hideOther();
                if (!add.length){
                    $.get('event/add.html', function(data){
                        main_content_holder.append(data);
                    });
                }else{
                    add.removeClass('hidden');
                }
            }
            else if (child_menu === 'detail'){
                var detail = $('#event_detail');
                hideOther();
                if (!detail.length){
                    $.get('event/detail.html', function(data){
                        main_content_holder.append(data);
                        onDetailEventLoad();
                    });
                }else{
                    detail.removeClass('hidden');
                }
            }
            break;
        case 'cadre':
            if (child_menu === 'add'){
                var add = $('#cadre_add');
                hideOther();
                if (!add.length){
                    $.get('cadre/add.html', function(data){
                        main_content_holder.append(data);
                    });
                }else{
                    add.removeClass('hidden');
                }
            }
            else if (child_menu === 'detail'){
                var detail = $('#cadre_detail');
                hideOther();
                if (!detail.length){
                    $.get('cadre/detail.html', function(data){
                        main_content_holder.append(data);
                        onDetailCadreLoad();
                    });
                }else{
                    detail.removeClass('hidden');
                }
            }
            break;
        case 'opinion':
            if (child_menu === 'add'){
                var add = $('#opinion_add');
                hideOther();
                if (!add.length){
                    $.get('opinion/add.html', function(data){
                        main_content_holder.append(data);
                    });
                }else{
                    add.removeClass('hidden');
                }
            }
            else if (child_menu === 'detail'){
                var detail = $('#opinion_detail');
                hideOther();
                if (!detail.length){
                    $.get('opinion/detail.html', function(data){
                        main_content_holder.append(data);
                        onDetailOpinionLoad();
                    });
                }else{
                    detail.removeClass('hidden');
                }
            }
            break;
        case 'business':
            if (child_menu === 'add'){
                var add = $('#business_add');
                hideOther();
                if (!add.length){
                    $.get('business/add.html', function(data){
                        main_content_holder.append(data);
                    });
                }else{
                    add.removeClass('hidden');
                }
            }
            else if (child_menu === 'detail'){
                var detail = $('#business_detail');
                hideOther();
                if (!detail.length){
                    $.get('business/detail.html', function(data){
                        main_content_holder.append(data);
                        onDetailBusinessLoad();
                    });
                }else{
                    detail.removeClass('hidden');
                }
            }
            break;
        case 'aspiration':
            hideOther();
            var aspiration = $('#aspiration');
            if (!aspiration.length){
                $.get('aspiration.html', function(data){
                    main_content_holder.append(data);
                    onDetailAspirationLoad();
                });
            }else{
                aspiration.removeClass('hidden');
            }
            break;
        case 'credit':
            main_content_holder.load('credits.html');
            hideOther();
            var credits = $('#credits');
            if (!credits.length){
                $.get('credits.html', function(data){
                    main_content_holder.append(data);
                    onDetailAspirationLoad();
                });
            }else{
                credits.removeClass('hidden');
            }
            break;
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
            $('#main-content').load('dashboard.html', onDashBoardLoad);
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