# BULLY ALGORITHM

- A GUI app that implements IPC via the bully algorithm.

### How to use:
- When the app starts click on "Raise" to start as many processes as you want.
- You can view any of the processes' mailboxes by selecting its Id from the dropdown list on the top right.
- the text area displays the mailbox of the selected process which is constantly updated to show new messages.
- Clicking "Kill" would kill the current coordinator initiating another round of election until a new coordinator wins.

### Method used:
- This implementation of Bully Algorithm uses the Messaging Passing Method.
- Messages are exchanged using communication links (ports).
- Sender send messages asynchrously; sends and continues.
- Receiver is blocked until a message is received.
- The message passing model is much more straight forward to implement than the shared memory model.
- Message passing also resembles API building and consumption which is a familiar concept that's easy to understand and work with.
- Another thing is Shared Memory method could cause problems if two processes try to access the memory at the same time which could lead to errors and unpredictable behaviour. 