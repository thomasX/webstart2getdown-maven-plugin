package info.margreiter.getdown.maven.plugin.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Inherited;
import java.nio.ByteOrder;

import javax.imageio.stream.IIOByteBuffer;
import javax.imageio.stream.ImageOutputStream;

import org.apache.maven.plugin.MojoExecutionException;

public class Util {

		public Util() {
		super();
		// TODO Auto-generated constructor stub
	}

	//TODO Test 14.11.2018
    public  void makeDirectoryIfNecessary( File dir ) throws MojoExecutionException
    {

        if ( !dir.exists() && !dir.mkdirs() )
        {
            throw new MojoExecutionException( "Failed to create directory: " + dir );
        }

    }
}
