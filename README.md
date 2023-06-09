# wikimusic<br />

This Single activity multiple fragment app built with MVVM pattern<br />

Library used :-<br />
Dependency injection - Hilt<br />
Navigation - Navigiation graph api<br />
Networking - Retrofit<br />
Debug Network result logging - Chucker<br />
Image loading - Glide<br />

# Thought process<br />

After reading the requirement carefully the first step i done is<br />

1. Define the styles<br />
2. Define the all resources components like fonts dimensions color etc<br />
3. Unaderstand the API and Make model class related to each api requirement<br />
4. Write the Network client using Retrofit and hilt<br />
5. Define the Network services which mentioned in problem statement.<br />
6. Design the common components of XML. eg - Recycler items, toolbar layout, progress layout<br />
7. Design the Navigation graph
8. Write the Common Helper Class<br />
9. Wrtie the viewmodel with uses of Live Data for each netowrk call.<br />
10. Then startet consuming these all the files and method in My Fragments.<br /><br />

# Improvements<br />
I used canary leak and here the some memory leak detected. It can be improved by making the binding null in each fragments in onDestroyView Methods.
Second approach would be the making a deligate class which work to make the view references null when fragment goes in to destroy lifecycle and use that delegate class in each fragments.



SCREENSHOTS

![IMG-20230403-WA0005](https://user-images.githubusercontent.com/65567507/229457375-a1b38c12-2044-49c0-af6f-44203378e0b1.jpg)
![IMG-20230403-WA0001](https://user-images.githubusercontent.com/65567507/229457387-13295c7f-c5b9-4fcd-90eb-acb90ff8ca1f.jpg)
![IMG-20230403-WA0003](https://user-images.githubusercontent.com/65567507/229457393-7ab3e657-8239-4ef8-be42-dc3ce53294c2.jpg)
![IMG-20230403-WA0007](https://user-images.githubusercontent.com/65567507/229457396-6fd8e445-99fb-4bab-b899-eb0980f4c732.jpg)
![IMG-20230403-WA0004](https://user-images.githubusercontent.com/65567507/229457401-3d3f3c64-94c8-4fd5-8e3b-29568c363ed4.jpg)
![IMG-20230403-WA0006](https://user-images.githubusercontent.com/65567507/229457404-c2e68d4e-7579-42b4-b789-bc3aeedd1a0f.jpg)
![img-20230403-wa0002](https://user-images.githubusercontent.com/65567507/229457408-5403f621-09fe-4528-8468-74a8731faa1f.jpeg)

