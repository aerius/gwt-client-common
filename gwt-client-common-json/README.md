# GWT Json Parser Wrapper

This is a simple GWT JSON Parser wrapper library adding syntactic sugar for parsing JSON using the ordinary GWT JSON parsing utilities.

## Installation

Add the maven dependency to your pom's `<depencencies>` section:

```xml
    <dependency>
      <groupId>nl.aerius</groupId>
      <artifactId>gwt-client-common-json</artifactId>
      <version>1.0</version>
    </dependency>
```

Add the following line to your GWT Module:

```xml
  <inherits name='nl.aerius.Json' />
```

## Usage

### Fetching fields

Given the following simple JSON String:

```JSON
{
  "foo": "bar"
}
```

The bar value can be retrieved as follows:

```java
// Define the above JSON String
String json = "{\"foo\":\"bar\"}";
JSONObjectHandle handle = JSONObjectHandle.from(json);

// Retrieve the 'foo' field
String foo = handle.getString("foo");
```

### Arrays

Fetching array data is similarly made simple, given:

```json
{
  "list": ["foo", "bar", "baz"]
}
```

The list values can be iterated over:

```java
String json = "{\"list\":[\"foo\",\"bar\",\"baz\"]}";
JSONObjectHandle handle = JSONObjectHandle.fromText(json);

// Iterate over array values
handle.getArray("list")
  .forEachString(GWT::log);
```

Or for a complex array:


```json
{
  "list": [
            { "value": "foo" },
            { "value": "bar" },
            { "value": "baz" }
          ]
}

```

The `value` for each respective item can be retrieved as follows:

```java
String json = "{\"list\": [{ \"value\": \"foo\" },{ \"value\": \"bar\" },{ \"value\": \"baz\" }]}";
JSONObjectHandle handle = JSONObjectHandle.fromText(json);

// Fetch the 'value' field for each object
handle.getArray("list")
  .forEach(obj -> GWT.log(obj.getString("value")));
```

### Requests

A simple `RequestUtil` is available to easily prepare web requests and, using the above parsing utilities, interpret the response.

Given a simple GET service endpoint that will return, for example, a `User`-type object of the following form:

```json
{
  id: 42
  name: "John Doe"
  age: 21
  location: "Utrecht"
}
```

Assume a service is running accepting GET requests at `https://users.myapplication.xyz/users/{id}`, responding in the above format.

A simple parser may be written to convert this response to a `User` object:

```java

public final class UserJsonParser implements AsyncCallback<String> {
  private final AsyncCallback<User> callback;

  public UserJsonParser(final AsyncCallback<User> callback) {
    this.callback = callback;
  }

  @Override
  public void onSuccess(final String result) {
    callback.onSuccess(parse(result));
  }

  public static final User parse(final String text) {
    final JSONObjectHandle handle = JSONObjectHandle.fromText(text);

    final User user = new User();
    user.setId(handle.getInteger("id"));
    user.setName(handle.getString("name"));
    user.setAge(handle.getInteger("age"));
    user.setLocation(handle.getString("location"));
    return user;
  }
}
```

A `User` may be retrieved and created on the client as follows:

```java
  public void fetchUser(final String id, final AsyncCallback<User> callback) {
    final String url = RequestUtil.prepareUrl("http://users.myapplication.xyz", "users/{id}", "{id}", id);

    RequestUtil.doGet(url, c -> new UserJsonParser(c), callback);
  }
```

### Advanced

An AutoValue processor library exists to automatically generate JSON Parsers such as the one described above based on an `@AutoValue` object type.

This processing library and instructions to use it is available at http://github.com/aerius/autovalue-processors
