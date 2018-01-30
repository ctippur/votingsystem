terraform {
  backend "s3" {
    bucket = "votingsystem-tf"
    key    = "terraform/terraform.tfstate"
  }
  profile = "default"
  region  = "${var.aws_region}"
}
module "base" {
    source = "../../resources/base"
    aws_region = "${var.aws_region}"
}

module "ddb" {
    source = "../../resources/dynamodb"
    region = "${var.aws_region}"
    environment = "${var.environment}"
}
module "s3" {
    source = "../../resources/s3"
    region = "${var.aws_region}"
}
#module "cloudfront" {
#    source = "../../resources/cloudfront"
#    region = "${var.aws_region}"
#}
