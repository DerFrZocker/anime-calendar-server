Anime-Calendar
======

Anime-Calendar is an anime iCal service, to sync your airing anime with your calendar, such as google calendar, outlook,
iphone and many more.

<p>

If the anime is licensed, it will show the time when it is available to stream, otherwise it will show the japan airing time (Converted to your local time).

## Usage:

As of now the setup is a bit complicated. It is however planed to sync it with your public anime list profile in the future.

<p>

Before you can sync your animes to your calendar you need to let anime-calendar know which animes you watch / want to keep track of, for this you need to make a `PUT` request to the following REST Endpoint: 

`https://api.anime-calendar.com/v2/personal/anime-calendar/<secret-id>`

For this you can use a service such as [restninja](
https://restninja.io/share/00a0efceadbc15775250a24e03d94bc2899e7097/0).

In the url you need to replace `<secret-id>`, with a unique id, since every one can put to any id, it is recommended to use a random string, to avoid collisions.
The id must only contain upper / lower case letters and number with a max length of 128.

In the body you need to put a JsonArray with the ids of the animes you watch. You can find a list of animes and there ids [here](https://github.com/DerFrZocker/anime-calendar-server/wiki/Anime-List).

After you send the put request you can add the following url to your calendar, where `<secret-id>` is the id you used in the put request.

`https://api.anime-calendar.com/v2/personal/anime-calendar/<secret-id>/DE_DE`

How you add an iCal feed to your calendar, depends on the calendar which you are using. Simple search
for `How to add iCal feed to <calendar>` and there should be guid for it.
