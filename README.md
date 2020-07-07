bayes-dota
==========

This is the [task](TASK.md).

Any additional information about your solution goes here:

I used Pattern matching to define the type of event for processing and at the same time to extract data from the log line needed for event creation.

Then all these events are stored into h2 database with append-only principle.
 
To prepare the result for requests I used native SQL queries and use Spring projection to translate it into POJO model.
If the change of rest models is not prohibited, I would add a constructor there and use those rest models directly as a transformation result for Query.
It will remove another hop of copyProperties from one bean to another and clean this service a bit more.