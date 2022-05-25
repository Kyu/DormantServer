package me.preciouso.dormantservers.utils;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.*;
import me.preciouso.dormantservers.DormantServers;
import net.md_5.bungee.config.Configuration;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class AwsHelper {
    private static AmazonEC2Client ec2Client;
    private static String instanceId;
    private static Instance myEc2Instance;
    private static AwsHelper instance;


    public static AwsHelper build() {
        return instance;
    }

    public static AwsHelper build(DormantServers plugin) {
        if (instance == null) {
            instance = new AwsHelper(plugin);
        }
        return instance;
    }

    private AwsHelper(DormantServers plugin) {

        Configuration cfg = plugin.getConfig();

        String access_key_id = cfg.getString("aws.login.access_key_id");
        String secret_access_key = cfg.getString("aws.login.aws_secret_access_key");
        instanceId = cfg.getString("aws.instance.id");

        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(access_key_id, secret_access_key);
        ec2Client = (AmazonEC2Client) AmazonEC2Client.builder().withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();

        try {
            ec2Client.describeInstances();
        } catch (AmazonEC2Exception error) {
            plugin.getLogger().log(Level.WARNING, error.getMessage());
            throw new RuntimeException("Could not validate AWS credentials.");
        }
    }

    public void startEc2Instance() {
        new StartInstancesRequest(Collections.singletonList(instanceId));
    }

    public void refreshEc2Instance() {
        DescribeInstancesRequest req = new DescribeInstancesRequest().withInstanceIds(instanceId);
        DescribeInstancesResult res =  ec2Client.describeInstances(req);

        if (! res.getReservations().isEmpty()) {
            Reservation reservation = res.getReservations().get(0);
            List<Instance> instances = reservation.getInstances();
            if (instances != null && ! instances.isEmpty()) {
                setMyEc2Instance(instances.get(0));
                return;
            }
        }

        setMyEc2Instance(null);
    }

    public Instance getMyEc2Instance() {
        return myEc2Instance;
    }

    private void setMyEc2Instance(Instance inst) {
        myEc2Instance = inst;
    }


}
