
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.basho.riak.client.*;
import com.basho.riak.client.bucket.*;
import com.basho.riak.client.raw.pbc.*;
import com.basho.riak.client.builders.*;
public class Index {
	public static void main (String[] argv) throws IOException, RiakException, RiakRetryFailedException {

		PBClientConfig conf = new PBClientConfig.Builder()
                            .withHost("127.0.0.1")
                             .withPort(8087)
                           .build();

		String bucketName = "meddata";

		IRiakClient client = RiakFactory.newClient(conf);
                Scanner scan = new Scanner(new File("../ohsumed.flat"));
                String line;

		Bucket myBucket = client.createBucket(bucketName).
			//enableForSearch().
			execute();
		Integer i = 0;

                while (scan.hasNextLine()) {
			i++;
                        line = scan.nextLine().trim();

			if (line.length() == 0) {
				return;
			}

			String data[] = line.split("\t");

			if (i % 1000 == 0 )
			System.out.println(i.toString());

			myBucket.store(data[0], data[1]).execute();		
	        }

		System.out.println("end");

		return;
        }
}
