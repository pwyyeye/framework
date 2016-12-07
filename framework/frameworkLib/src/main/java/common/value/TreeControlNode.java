package common.value;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <p>
 * An individual node of a tree control represented by an instance of
 * <code>TreeControl</code>, and rendered by an instance of
 * <code>TreeControlTag</code>.
 * </p>
 * 
 * @author Jazmin Jonson
 * @author Craig R. McClanahan
 * @version $Revision: 1.1 $ $Date: 2015/09/21 14:34:27 $
 */

public class TreeControlNode implements Serializable {

	private Object object;

	private String action;

	private String icon;

	private String target;

	private String rightCode;

	private String frame;
	
	private boolean singleMode;

	// ----------------------------------------------------------- Constructors

	public boolean isSingleMode() {
		return singleMode;
	}

	public void setSingleMode(boolean singleMode) {
		this.singleMode = singleMode;
	}

	public String getFrame() {
		return frame;
	}

	public void setFrame(String frame) {
		this.frame = frame;
	}

	public String getRightCode() {
		return rightCode;
	}

	public void setRightCode(String rightCode) {
		this.rightCode = rightCode;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * Construct a new TreeControlNode with the specified parameters.
	 * 
	 * @param name
	 *            Internal name of this node (must be unique within the entire
	 *            tree)
	 * @param icon
	 *            Pathname of the image file for the icon to be displayed when
	 *            this node is visible, relative to the image directory for our
	 *            images
	 * @param label
	 *            The label that will be displayed to the user if this node is
	 *            visible
	 * @param action
	 *            The hyperlink to be selected if the user selects this node, or
	 *            <code>null</code> if this node's label should not be a
	 *            hyperlink
	 * @param target
	 *            The window target in which the <code>action</code>
	 *            hyperlink's results will be displayed, or <code>null</code>
	 *            for the current window
	 * @param expanded
	 *            Should this node be expanded?
	 */
	public TreeControlNode(String id, String name, String action,
			Object object, boolean expanded, String icon, String target) {

		super();
		this.id = id;
		this.name = name;
		this.expanded = expanded;
		this.object = object;
		this.action = action;
		this.icon = icon;
		this.target = target;
	}

	public TreeControlNode(String id, String name, String action, String frame,
			String rightCode, Object object, boolean expanded, String icon,
			String target) {

		this(id, name, action, object, expanded, icon, target);
		this.rightCode=rightCode;
		this.frame=frame;
	}
	public TreeControlNode(String id, String name, String action, String frame,
			String rightCode, Object object, boolean expanded, String icon,
			String target,boolean singleMode) {

		this(id, name, action, object, expanded, icon, target);
		this.rightCode=rightCode;
		this.frame=frame;
		this.singleMode=singleMode;
	}

	public TreeControlNode(String id, String name, Object object,
			boolean expanded) {
		super();
		this.id = id;
		this.name = name;
		this.expanded = expanded;
		this.object = object;
		this.action = null;
		this.target = null;

	}

	// ----------------------------------------------------- Instance Variables

	/**
	 * The set of child <code>TreeControlNodes</code> for this node, in the
	 * order that they should be displayed.
	 */
	protected ArrayList children = new ArrayList();

	// ------------------------------------------------------------- Properties

	/**
	 * The hyperlink to which control will be directed if this node is selected
	 * by the user.
	 */

	/**
	 * The label that will be displayed when this node is visible.
	 */
	protected String name = null;

	public String getName() {
		return (this.name);
	}

	private boolean expanded;

	public boolean isExpanded() {
		return (this.expanded);
	}

	/**
	 * Is this the last node in the set of children for our parent node?
	 */
	protected boolean last = false;

	public boolean isLast() {
		return (this.last);
	}

	void setLast(boolean last) {
		this.last = last;
	}

	/**
	 * Is this a "leaf" node (i.e. one with no children)?
	 */
	public boolean isLeaf() {
		synchronized (children) {
			return (children.size() < 1);
		}
	}

	/**
	 * The unique (within the entire tree) name of this node.
	 */
	protected String id = null;

	/**
	 * The parent node of this node, or <code>null</code> if this is the root
	 * node.
	 */
	protected TreeControlNode parent = null;

	public TreeControlNode getParent() {
		return (this.parent);
	}

	void setParent(TreeControlNode parent) {
		this.parent = parent;
		if (parent == null)
			width = 1;
		else
			width = parent.getWidth() + 1;
	}

	/**
	 * Is this node currently selected?
	 */
	protected boolean selected = false;

	public boolean isSelected() {
		return (this.selected);
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * The <code>TreeControl</code> instance representing the entire tree.
	 */
	protected TreeControl tree = null;

	public TreeControl getTree() {
		return (this.tree);
	}

	void setTree(TreeControl tree) {
		this.tree = tree;
	}

	/**
	 * The display width necessary to display this item (if it is visible). If
	 * this item is not visible, the calculated width will be that of our most
	 * immediately visible parent.
	 */
	protected int width = 0;

	public int getWidth() {
		return (this.width);
	}

	// --------------------------------------------------------- Public Methods

	/**
	 * Add a new child node to the end of the list.
	 * 
	 * @param child
	 *            The new child node
	 * 
	 * @exception IllegalArgumentException
	 *                if the name of the new child node is not unique
	 */
	public void addChild(TreeControlNode child) throws IllegalArgumentException {

		tree.addNode(child);
		child.setParent(this);
		synchronized (children) {
			int n = children.size();
			if (n > 0) {
				TreeControlNode node = (TreeControlNode) children.get(n - 1);
				node.setLast(false);
			}
			child.setLast(true);
			children.add(child);
		}

	}

	/**
	 * Add a new child node at the specified position in the child list.
	 * 
	 * @param offset
	 *            Zero-relative offset at which the new node should be inserted
	 * @param child
	 *            The new child node
	 * 
	 * @exception IllegalArgumentException
	 *                if the name of the new child node is not unique
	 */
	public void addChild(int offset, TreeControlNode child)
			throws IllegalArgumentException {

		tree.addNode(child);
		child.setParent(this);
		synchronized (children) {
			children.add(offset, child);
		}

	}

	/**
	 * Return the set of child nodes for this node.
	 */
	public TreeControlNode[] findChildren() {

		synchronized (children) {
			TreeControlNode results[] = new TreeControlNode[children.size()];
			return ((TreeControlNode[]) children.toArray(results));
		}

	}

	/**
	 * Remove this node from the tree.
	 */
	public void remove() {

		if (tree != null) {
			tree.removeNode(this);
		}

	}

	/**
	 * Remove the child node (and all children of that child) at the specified
	 * position in the child list.
	 * 
	 * @param offset
	 *            Zero-relative offset at which the existing node should be
	 *            removed
	 */
	public void removeChild(int offset) {

		synchronized (children) {
			TreeControlNode child = (TreeControlNode) children.get(offset);
			tree.removeNode(child);
			child.setParent(null);
			children.remove(offset);
		}

	}

	// -------------------------------------------------------- Package Methods

	/**
	 * Remove the specified child node. It is assumed that all of the children
	 * of this child node have already been removed.
	 * 
	 * @param child
	 *            Child node to be removed
	 */
	void removeChild(TreeControlNode child) {

		if (child == null) {
			return;
		}
		synchronized (children) {
			int n = children.size();
			for (int i = 0; i < n; i++) {
				if (child == (TreeControlNode) children.get(i)) {
					children.remove(i);
					return;
				}
			}
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

}