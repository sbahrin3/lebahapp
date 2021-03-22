# LeBAH Framework WebApp Template

This is an Eclipse based project, so you need to have Eclipse installed first.

## About

LeBAH is a lightweight Java WebApp MVC Framework that I started developed in the year 2007,  which during that time, there is not much Java WebApp framework available.  Over the years it has evolved to become more stable and easier for developer to use.  The most distinguishing feature of this framework compared to others is that every request and submission is done automatically via Ajax call.  

## Setup

As this repository is an Eclipse-based project, you just need to clone it into your Eclipse IDE.  Watch this video on how to clone the project into your own Eclipse.


[[Watch the how to video]](https://www.youtube.com/embed/KX52hVjiUbg)


## Embedded H2 Database

This project came with an embedded H2 Database.  You are not needed to install any database to run this application.  The database connection information are as below:

```sh
title.default=LIGHT AND EASY
driver.default=org.h2.Driver
dialect.default=org.hibernate.dialect.H2Dialect
user.default=sa
password.default=
url.default=jdbc:h2:file:~/h2db/demodb
```


## MySQL Database

Anyway, you can use other database, like MySQL, PostgresQL, and others which you need to install them separately yourself.  

Then you need to modify the dbpersistence.properties file located in the Java Recourse\src folder of the project. Edit the configuration to your environment.

```sh
driver.default=com.mysql.jdbc.Driver
user.default=root
password.default=
url.default=jdbc:mysql://localhost:3306/demodb?serverTimezone=UTC
```

## Start

Your Eclipse environment must have Application Server configured.  To run the application, right click on the project name and choose Run As > Run on server... and select your Application Server to run.


## Example Module

A Module must follow these rules:

1. A module must extends the LebahModule.
2. It must have a start() method that return the start page template, usually start.vm.
3. Every method to be called from the view page must begin with @Command annotation with the request parameter name.

As an example below:

- First we define the location of the views template, which is "apps/hellowWorld".
- Then we have the start() method that return the start.vm.
- Then we have the save() method which will be called when request is done on the "saveFood" command from the view page, and when this method done executed, it will return the page "result.vm".


```java
public class HelloWorldModule extends LebahModule {
	private String path = "apps/helloWorld";

	@Override
	public String start() {
		return path + "/start.vm";
	}
	
	@Command("saveFood")
	public String save() {
		String favoriteFood = getParam("favorite_food");
		Person person = db.find(Person.class, ((User) context.get("user")).getId());
		person.setFavoriteFood(favoriteFood);
		db.update(person);
		context.put("person", person);
		return path + "/result.vm";
	}
	 
}
```

This module has views that are located in the folder "apps" of the application directory.  In this apps folder, there is a "helloWorld" sub-folder.  Within this sub-folder, we shall have all the pages needed by our modules, as shown below.

```
	lebah <root>
		- apps <folder>
			- helloWorld <folder>
				start.vm <page>
				result.vm <page>
```

Whenever the module is run, it will call the start() method.  In this example, it will only return the page "start.vm".

This page contains HTML codes as below:

```html
<h1>
Hello $user.firstName
</h1>
<h2>
Apakah makanan kegemaran anda?
</h2>

<div class="row">
	<div class="col-sm-6">
		<input class="form-control" type="text" name="favorite_food">
	</div>
	<div class="col-sm-6">
		<button type="button" class="btn btn-primary" onclick="sendAjax('saveFood','div_favorite_food','')">Hantar</button>
	</div>
</div>


<div id="div_favorite_food"></div>
```

As you can see, there is no form element defined here.  This is the lebah way - you're not needed to include form elements inside your HTML page.

The $user is a variable.  Anything that begins with a $ sign will be treated as a variable.  What this variable holds (or it's value) is determined by the your lebah's module or it is a pre-defined variable by the lebah framework.  In this case, it is a pre-defined user object.

On this page, user will be asked to enter her favorite food, then submit it by clicking the submit button.  As you can see, this button onclick event shall call the sendAjax() method.

The sendAjax() is a JS method defined by the framework.  It consists of three parameters:

- the command name, which will determined what method will be called by the Java codes.
- the div's element name in this page, where the resulted views will be rendered.
- the query string to be sent with this request.

This JS method will request a view page from the server.  On the server, this is result.vm.  It consists of below codes:

```html

<div class="well">
<h2>Makanan kegemaran anda ialah $person.favoriteFood</h2>
</div>

```

As you can see, there is one variable here, $person.  This variable actually hold a Person object, that has a favoriteFood attribute.


