package com.huateng.frame.maven;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.ibatis.ibator.api.Ibator;
import org.apache.ibatis.ibator.config.IbatorConfiguration;
import org.apache.ibatis.ibator.config.xml.IbatorConfigurationParser;
import org.apache.ibatis.ibator.exception.InvalidConfigurationException;
import org.apache.ibatis.ibator.exception.XMLParserException;
import org.apache.ibatis.ibator.internal.DefaultShellCallback;
import org.apache.ibatis.ibator.internal.util.StringUtility;
import org.apache.ibatis.ibator.internal.util.messages.Messages;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * 用于调用ibator的mojo
 * 
 * @goal ibator
 * 
 * @phase generate-sources
 * 
 * @author chenjun.li
 */
public class IbatorMojo extends AbstractMojo {
	
	/**
	 * @parameter
	 */
	private File configfile;
	
	/**
	 * @parameter
	 */
	private String fullyQualifiedTableNames;
	
	/**
	 * @parameter
	 */
	private String contextIds;
	
	/**
	 * @parameter
	 */
	private Properties properties;
	
	/**
	 * @parameter
	 */
	private boolean overwrite;

	public void execute() throws MojoExecutionException, MojoFailureException
	{
        if (configfile == null) {
            throw new MojoExecutionException(Messages.getString("RuntimeError.0")); //$NON-NLS-1$
        }
        List<String> warnings = new ArrayList<String>();
        
        File configurationFile = configfile;
        if (!configurationFile.exists()) {
            throw new MojoExecutionException("文件没找到："+configfile); //$NON-NLS-1$
        }
        
        Set<String> fullyqualifiedTables = new HashSet<String>();
        if (StringUtility.stringHasValue(fullyQualifiedTableNames)) {
            StringTokenizer st = new StringTokenizer(fullyQualifiedTableNames, ","); //$NON-NLS-1$
            while (st.hasMoreTokens()) {
                String s = st.nextToken().trim();
                if (s.length() > 0) {
                    fullyqualifiedTables.add(s);
                }
            }
        }
        
        Set<String> contexts = new HashSet<String>();
        if (StringUtility.stringHasValue(contextIds)) {
            StringTokenizer st = new StringTokenizer(contextIds, ","); //$NON-NLS-1$
            while (st.hasMoreTokens()) {
                String s = st.nextToken().trim();
                if (s.length() > 0) {
                    contexts.add(s);
                }
            }
        }
      
        try {
            Properties p = properties;

            IbatorConfigurationParser cp = new IbatorConfigurationParser(p,
                warnings);
            IbatorConfiguration config = cp.parseIbatorConfiguration(configurationFile);
           
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
           
            Ibator ibator = new Ibator(config, callback, warnings);
          
            ibator.generate(new IbatorMavenProgressCallback(getLog()), contexts, fullyqualifiedTables);
          
        } catch (XMLParserException e) {
            for (String error : e.getErrors()) {
            	getLog().error(error);
            }
            
            throw new MojoExecutionException(e.getMessage());
        } catch (SQLException e) {
            throw new MojoExecutionException(e.getMessage());
        } catch (IOException e) {
            throw new MojoExecutionException(e.getMessage());
        } catch (InvalidConfigurationException e) {
            for (String error : e.getErrors()) {
            	getLog().error(error);
            }
            
            throw new MojoExecutionException(e.getMessage());
        } catch (InterruptedException e) {
            // ignore (will never happen with the DefaultShellCallback)
            ;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MojoExecutionException(e.getMessage());
        }
        
        for (String error : warnings) {
        	getLog().warn(error);
        }
	}

	public String getFullyQualifiedTableNames() {
		return fullyQualifiedTableNames;
	}

	public void setFullyQualifiedTableNames(String fullyQualifiedTableNames) {
		this.fullyQualifiedTableNames = fullyQualifiedTableNames;
	}

	public String getContextIds() {
		return contextIds;
	}

	public void setContextIds(String contextIds) {
		this.contextIds = contextIds;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public boolean isOverwrite() {
		return overwrite;
	}

	public void setOverwrite(boolean overwrite) {
		this.overwrite = overwrite;
	}

	public File getConfigfile() {
		return configfile;
	}

	public void setConfigfile(File configfile) {
		this.configfile = configfile;
	}

}
