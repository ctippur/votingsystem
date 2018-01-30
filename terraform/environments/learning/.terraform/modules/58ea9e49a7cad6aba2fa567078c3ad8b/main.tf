resource "aws_iam_role" "iam_for_lambda" {
  name = "iam_for_lambda"

  assume_role_policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": "sts:AssumeRole",
      "Principal": {
        "Service": "lambda.amazonaws.com"
      },
      "Effect": "Allow",
      "Sid": ""
    }
  ]
}
EOF
}

resource "aws_dynamodb_table" "user-table" {
  name           = "User"
  read_capacity  = 5
  write_capacity = 5
  hash_key       = "UserId"

  attribute {
    name = "UserId"
    type = "S"
  }

  ttl {
    attribute_name = "TimeToExist"
    enabled = false
  }

  tags {
    Name        = "User-table"
    Environment = "${var.environment}"
  }
}

resource "aws_dynamodb_table" "vote-table" {
  name           = "Vote"
  read_capacity  = 5
  write_capacity = 5
  hash_key       = "MediaId"

  attribute {
    name = "MediaId"
    type = "S"
  }

  ttl {
    attribute_name = "TimeToExist"
    enabled = false
  }

  tags {
    Name        = "Vote-table"
    Environment = "${var.environment}"
  }
}

resource "aws_dynamodb_table" "media-table" {
  name           = "Media"
  read_capacity  = 5
  write_capacity = 5
  hash_key       = "MediaId"

  attribute {
    name = "MediaId"
    type = "S"
  }

  ttl {
    attribute_name = "TimeToExist"
    enabled = false
  }

  tags {
    Name        = "Media-table"
    Environment = "${var.environment}"
  }
}
