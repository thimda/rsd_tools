/*
 * Created on 2005-9-1
 * @author ºÎ¹ÚÓî
 */
package nc.uap.lfw.internal.util;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

/*
 * @author ºÎ¹ÚÓî
 */
public class FullCopyVisitor implements IResourceVisitor {

    private IFolder to;

    public FullCopyVisitor(IFolder target) {
        this.to = target;
    }

    public boolean visit(IResource resource) throws CoreException {
        if (resource.getType() == IResource.FILE) {
            IFile fromFile = (IFile) resource;

            IPath path = getOutputPath(fromFile);
            IFile toFile = to.getFile(path);

            ProjCoreUtility.createFolder((IFolder) toFile.getParent());
            InputStream in = null;
            try {
                in = fromFile.getContents();
                toFile.create(in, true, null);
            } finally {
                try {
                    in.close();
                } catch (IOException e) {

                }
            }
            return false;

        } else if (resource.getType() == IResource.FOLDER) {
            IFolder folder = to.getFolder(resource.getProjectRelativePath());
            ProjCoreUtility.createFolder(folder);
            return true;
        }
        return true;
    }
    
    public IPath getOutputPath(IFile file) {
        IPath path = file.getProjectRelativePath().removeLastSegments(1);

        path = path.append(file.getName());

        return path;

    }

}
