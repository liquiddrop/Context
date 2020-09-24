# Context
This is an android application for storing and displaying

historical data. You can search through the database by

location, date, or search for keywords in the event.

I though this would be usefull for providing

historical context for things seen while traveling. You can also

see timelines side by side

Here are a couple screen shots of the app:

<img src="https://github.com/liquiddrop/Context/blob/master/docs/Screenshot_Context_1.jpg" alt="Context Homepage" width="200"> <img src="https://github.com/liquiddrop/Context/blob/master/docs/Screenshot_Context_2.jpg" alt="Context Single Timeline" width="200"> <img src="https://github.com/liquiddrop/Context/blob/master/docs/Screenshot_Context_3.jpg" alt="Context Double Timeline" width="200">

## The details

This app was made in android studio using the room database for

storing historical events and a recycler view to display the data.

The historical events are scraped from wikipedia using python which

is in the repo "python_create_database". The database is not included

in this repository. The layout of the timeline list is using this

open sorce repository [Timeline layout](https://github.com/vipulasri/Timeline-View)
