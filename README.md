


##  Overview
This project is a RESTful Smart Campus API built using JAX-RS. It manages Rooms, Sensors, and Sensor Readings, allowing users to create rooms, assign sensors, filter sensors, and manage readings. The API uses a versioned base path (/api/v1) and includes proper error handling, logging, and business rules such as preventing deletion of rooms with active sensors.



## How to Run the Project



 1. Clone the Project (saved to Desktop for easy access through terminal)

```bash
cd ~/Desktop
```

```bash
git clone https://github.com/S-U-23/ClientServerCourseWork.git
```

2. Open in NetBeans
- Open NetBeans  
- Click File → Open Project  
- Navigate to Desktop → ClientServerCourseWork  
- Select the project and open it  



3. Run the Project
- Right-click the project  
- Click Run  
- Apache Tomcat should start automatically  


4. Access the API

Open in browser or Postman:

```
http://localhost:8080/Coursework1/api/v1
```



##  Sample CURL Commands

### 1. Discovery Endpoint

```bash
curl -X GET http://localhost:8080/Coursework1/api/v1
```

### 2. Create a Room

```bash
curl -X POST http://localhost:8080/Coursework1/api/v1/rooms \
-H "Content-Type: application/json" \
-d '{"id":"LAB-101","name":"Computer Lab","capacity":30}'
```

### 3. Get All Rooms

```bash
curl -X GET http://localhost:8080/Coursework1/api/v1/rooms
```

### 4. Create a Sensor

```bash
curl -X POST http://localhost:8080/Coursework1/api/v1/sensors \
-H "Content-Type: application/json" \
-d '{"id":"CO2-1","type":"CO2","status":"ACTIVE","currentValue":0,"roomId":"LAB-101"}'
```

### 5. Filter Sensors

```bash
curl -X GET "http://localhost:8080/Coursework1/api/v1/sensors?type=CO2"
```

---
# Answering the Questions 
## Question 1: 
 In your report, explain the default lifecycle of a JAX-RS Resource class. Is a new instance instantiated for every incoming request, or does the runtime treat it as a singleton? Elaborate on how this architectural decision impacts the way you manage and synchronize your in-memory data structures (maps/lists) to prevent data loss or race conditions.
## Answer: 
JAX-RS resource classes have a default lifecycle, often being request-scoped. For example  A new instance of the resource class is created when a new HTTP request comes in.This aids the principle of statelessness in REST where every request is treated as a singular request and has no common state among the resource class. Furtheremore, instance variables on the resource are not shared between the requests and the risks of interference between various clients are reduced to a minimum.However the full thread safety as the application can still use in-memory data structures, such as HashMaps or ArrayLists. These structures can come in many requests, and be updated in parallel can result in race conditions, inconsistent data or data loss. Due to this the per-request lifecycle provides better resource-level isolation, shared data must still be cautiously managed by the developer which, typically, requires centralising data into one storage class and updating in a controlled or thread-safe manner.
## Question 2: 
Question: Why is the provision of ”Hypermedia” (links and navigation within responses) considered a hallmark of advanced RESTful design (HATEOAS)? How does this approach benefit client developers compared to static documentation?
## Answer: 
Hypermedia is an advanced REST feature because the API includes links in its response to show what actions or endpoints can be used next. This is an advantage to developers because instead of relying only on a static documentation, they can see directly where the response comes  and how to interact with the API.
## Question 3:
Question: When returning a list of rooms, what are the implications of returning only
IDs versus returning the full room objects? Consider network bandwidth and client side
processing.
## Answer: 
By returning only the room Ids it uses less bandwidth, this is because the response is smaller meaning	 that it would be faster to send. if only the IDs are returned, the client would need to make an extra request for it (to get the full room details). By Returning all the room details it uses more bandwidth since the response is larger. This is because it would give the client all the information straight away (so no extra requests are needed).
## Question 4:
Question: Is the DELETE operation idempotent in your implementation? Provide a detailed
justification by describing what happens if a client mistakenly sends the exact same DELETE
request for a room multiple times
## Answer: 
The delete operation is idempotent. This Is because in my implementation, the first delete request removes the room if it exists and has no sensors. If the same request is sent again when the that room has been already deleted the API returns a 404 not found error. Even thought the response changes, the system state stays the same meaning that a repeated delete request do not cause any further changes, which means that the operation is idempotent
## Question 5:
Question: We explicitly use the @Consumes (MediaType.APPLICATION_JSON) annotation on
the POST method. Explain the technical consequences if a client attempts to send data in
a different format, such as text/plain or application/xml. How does JAX-RS handle this
mismatch?
## Answer:
The @Consumes(MediaType.APPLICATION_JSON) annotation ensures that the POST method can  only accepts requests in a certain format (JSON format). For example, If a client sends data in a different format like text/plain or application/xml This would cause  the JAX-RS not being able to process the request which  results in a 415 unsupported media type response. This result occurs since the request does not match the media type which is defined by the API.
## Question 6:
Question: You implemented this filtering using @QueryParam. Contrast this with an alternative design where the type is part of the URL path (e.g., /api/vl/sensors/type/CO2). Why
is the query parameter approach generally considered superior for filtering and searching
collections?
## Answer:
By using @QueryParam is generally better for filtering since it keeps the main resource path the same and only adds extra conditions to narrow down the result. As a result of this it makes more sense for searching collections, since /sensors represents the main resource whereas the ?type=CO2 is just a filter. By using the type as part of the Parth it makes it look like a separate resource rather than a search or filter.
## Question 7:
Question: Discuss the architectural benefits of the Sub-Resource Locator pattern. How
does delegating logic to separate classes help manage complexity in large APIs compared
to defining every nested path (e.g., sensors/{id}/readings/{rid}) in one massive controller class?
## Answer:
The Sub-Resource location pattern is extremity useful, this is because it separates the nested rescues into their own classes.By doing this it makes the API easier to organise and maintain. As a result of this it helps manage complexity in large APIs since each class is responsible for its own logic, instead of placing every nested path inside one large controller. Due to this it causes the code to become cleaner, easier to read and simpler to update
## Question 8
Question: Why is HTTP 422 often considered more semantically accurate than a standard
404 when the issue is a missing reference inside a valid JSON payload?
## Answer:
The HTTP 422 is more accurate than 404 because the request itself is valid but the data inside of it is incorrect. In this case the JSON format is correct but contain a reference such as a roomie that does not exist. A 404 is usually used when a resource itself cannot be found via the URL, whereas 422 is used to indicate that the request was understood but is not able to process due to invalid data
## Question 9:
Question: From a cybersecurity standpoint, explain the risks associated with exposing
internal Java stack traces to external API consumers. What specific information could an
attacker gather from such a trace?
## Answer:
By exposing the internal java stack trace it can create security risks. This is because it may uncover sensitive information regarding  the system. For instance when an attacker learns about isome important information such as the names of classes, file locations, methodologies or technologies in use. This would assist them in knowing how the system operates and may identify weaknesses that they can exploit.

## Question 10:
Question: Why is it advantageous to use JAX-RS filters for cross-cutting concerns like
logging, rather than manually inserting Logger.info() statements inside every single resource method?
## Answer:
By using JAX-RS filters for logging its  better since it allows placing it in a filter meaning  the logging logic is not repeated in many different place but rather in a single central location.  By doing this it makes the code cleaner, easier to maintain, and ensures consistent logging across the whole API.



