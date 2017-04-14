/**
 * Sabang Sigle Page App
 */

// Firebase imported in html
// JQuery imported in html

function onDashBoardLoad(){
    
}

function onDetailEventLoad(){
    var event_table = $('#event_detail_table');
    function addEventToTable(key, title, organizer, date){
        var new_element = 
        '<tr> \
            <td> ' + key + '</td> \
            <td><a href="basic_table.html#"> ' + title + '</a></td> \
            <td> ' + organizer + '</td> \
            <td> ' + date + '</td> \
            <td> \
                <button class="btn btn-primary btn-xs"><i class="fa fa-pencil"></i></button> \
                <button class="btn btn-danger btn-xs"><i class="fa fa-trash-o "></i></button> \
            </td> \
        </tr>';
        event_table.append(new_element);
    }
    
    function changeEventFromTable(key, title, organizer, date){
        
    }
    
    function removeEventFromTable(key){
        
    }
    
    var event_detail_Ref = firebase.database().ref('event/');
    event_detail_Ref.on('child_added', function(data) {
        addEventToTable(data.key, data.val().title, data.val().organizer, data.val().date_start);
    });
    
    event_detail_Ref.on('child_changed', function(data) {
        changeEventFromTable(data.key, data.val().title, data.val().organizer, data.val().date_start);
    });
    
    event_detail_Ref.on('child_removed', function(data) {
        addEventToTable(data.key);
    });
}

function onDetailCadreLoad(){
    var cadre_table = $('#cadre_detail_table');
    function addCadreToTable(key, title, organizer, date){
        var new_element = 
        '<tr> \
            <td> ' + key + '</td> \
            <td><a href="basic_table.html#"> ' + title + '</a></td> \
            <td> ' + organizer + '</td> \
            <td> ' + date + '</td> \
            <td> \
                <button class="btn btn-primary btn-xs"><i class="fa fa-pencil"></i></button> \
                <button class="btn btn-danger btn-xs"><i class="fa fa-trash-o "></i></button> \
            </td> \
        </tr>';
        cadre_table.append(new_element);
    }
    
    function changeCadreFromTable(key, title, organizer, date){
        
    }
    
    function removeCadreFromTable(key){
        
    }
    
    var cadre_detail_Ref = firebase.database().ref('cadre/');
    cadre_detail_Ref.on('child_added', function(data) {
        addCadreToTable(data.key, data.val().title, data.val().organizer, data.val().date_start);
    });
    
    cadre_detail_Ref.on('child_changed', function(data) {
        changeCadreFromTable(data.key, data.val().title, data.val().organizer, data.val().date_start);
    });
    
    cadre_detail_Ref.on('child_removed', function(data) {
        removeCadreFromTable(data.key);
    });
}

function onDetailOpinionLoad(){
    var opinion_table = $('#opinion_detail_table');
    function addOpinionToTable(key, title){
        var new_element = 
        '<tr> \
            <td> ' + key + '</td> \
            <td><a href="basic_table.html#"> ' + title + '</a></td> \
            <td> \
                <button class="btn btn-primary btn-xs"><i class="fa fa-pencil"></i></button> \
                <button class="btn btn-danger btn-xs"><i class="fa fa-trash-o "></i></button> \
            </td> \
        </tr>';
        opinion_table.append(new_element);
    }
    
    function changeOpinionFromTable(key, title){
        
    }
    
    function removeOpinionFromTable(key){
        
    }
    
    var opinion_detail_Ref = firebase.database().ref('opinion/');
    opinion_detail_Ref.on('child_added', function(data) {
        addOpinionToTable(data.key, data.val().title);
    });
    
    opinion_detail_Ref.on('child_changed', function(data) {
        changeOpinionFromTable(data.key, data.val().title);
    });
    
    opinion_detail_Ref.on('child_removed', function(data) {
        removeOpinionFromTable(data.key);
    });
}

function onDetailBusinessLoad(){
    var business_table = $('#business_detail_table');
    function addBusinessToTable(key, name, price){
        var new_element = 
        '<tr> \
            <td> ' + key + '</td> \
            <td><a href="basic_table.html#"> ' + name + '</a></td> \
            <td> ' + price + '</td> \
            <td> \
                <button class="btn btn-primary btn-xs"><i class="fa fa-pencil"></i></button> \
                <button class="btn btn-danger btn-xs"><i class="fa fa-trash-o "></i></button> \
            </td> \
        </tr>';
        business_table.append(new_element);
    }
    
    function changeBusinessFromTable(key, name, price){
        
    }
    
    function removeBusinessFromTable(key){
        
    }
    
    var business_detail_Ref = firebase.database().ref('business/');
    business_detail_Ref.on('child_added', function(data) {
        addBusinessToTable(data.key, data.val().name, data.val().price);
    });
    
    business_detail_Ref.on('child_changed', function(data) {
        changeBusinessFromTable(data.key, data.val().name, data.val().price);
    });
    
    business_detail_Ref.on('child_removed', function(data) {
        removeCadreFromTable(data.key);
    });
}

function onDetailAspirationLoad(){
    var aspiration_table = $('#aspiration_detail_table');
    function addAspirationToTable(key, message){
        var new_element = 
        '<tr> \
            <td> ' + key + '</td> \
            <td><a href="basic_table.html#"> ' + message + '</a></td> \
            <td> \
                <button class="btn btn-primary btn-xs"><i class="fa fa-pencil"></i></button> \
                <button class="btn btn-danger btn-xs"><i class="fa fa-trash-o "></i></button> \
            </td> \
        </tr>';
        aspiration_table.append(new_element);
    }
    
    function changeAspirationFromTable(key, message){
        
    }
    
    function removeAspirationFromTable(key){
        
    }
    
    var conversation_detail_Ref = firebase.database().ref('conversation/');
    conversation_detail_Ref.on('child_added', function(data) {
        var keys = []
        for (var key in data.val()){
            keys.push(key);
        }
        
        addAspirationToTable(data.key, data.val()[keys[keys.length-1]].text);
    });
    
    conversation_detail_Ref.on('child_changed', function(data) {
        changeAspirationFromTable(data.key, data.val().text);
    });
    
    conversation_detail_Ref.on('child_removed', function(data) {
        removeAspirationFromTable(data.key);
    });
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