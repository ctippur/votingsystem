provider "aws" {
  region     = "${var.aws_region}"
}


resource "aws_iam_role" "votingsystem-role" {
    name = "votingsystem-role"
    assume_role_policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": {
        "Service": "lambda.amazonaws.com"
      },
      "Action": "sts:AssumeRole"
    }
  ]
}
EOF
}

resource "aws_iam_role_policy" "-lambda-policy" {
    name = "votingsystem-lambda-policy"
    role = "${aws_iam_role.votingsystem-role.id}"
    policy = "${file("cyclops-policy.json")}"
}
