## Prerequisites

* AWS account
* AWS CLI
* SAM CLI v1.84.0 (1.85.0 has a bug)
* IntelliJ + AWS Toolkit plugin
* Docker

## Running locally

```shell
sam local invoke -e .\response.json
```

## Deploying to AWS 

```shell
sam deploy
```

## Executing lambda directly
First get the function name
```shell
aws cloudformation describe-stack-resource --stack-name=aws-starter-sam-app --logical-resource-id=AppFunction --query=StackResourceDetail.PhysicalResourceId
```

Then call that function
```shell
aws lambda invoke --function-name=aws-starter-sam-app-AppFunction-IZQ3XZHzRUjn response.json
```

Invoke the API via API gateway
```shell
curl --location --request POST 'https://t42v7vbhd2.execute-api.us-east-1.amazonaws.com/Staging/' \
--header 'Content-Type: application/json' \
--data-raw '{"id": 1, "name": "Joe Cool"}'
```
