aws cloudformation package --template-file sam.yaml --output-template-file output-sam.yaml --s3-bucket votingsystem-tf

aws cloudformation deploy --template-file output-sam.yaml --stack-name VotingSystemSample --capabilities CAPABILITY_IAM --region us-west-2

aws cloudformation describe-stacks --stack-name VotingSystemSample --region us-west-2
