package tech.jour.galleryclean

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.jour.galleryclean.databinding.FragmentSecondBinding
import tech.jour.galleryclean.databinding.ItemGroupImageBinding
import tech.jour.galleryclean.databinding.ItemListGroupBinding
import tech.jour.galleryclean.entry.Group
import tech.jour.galleryclean.entry.Photo
import tech.jour.galleryclean.util.SameSizePhoto
import tech.jour.galleryclean.util.SimilarPhoto

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.photos.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
//                findSimilarGroup(it)
                findSameSizeGroup(it)
            }
        }

        mainViewModel.groups.observe(viewLifecycleOwner) {
            val result = it.filter { g ->
                (g.photos.size) > 1
            }
            binding.list.adapter = GroupAdapter(result)
        }
    }

    private fun findSameSizeGroup(photos: List<Photo>) {
        lifecycleScope.launch(Dispatchers.IO) {
            val groups = SameSizePhoto(photos).find()
            mainViewModel.setGroups(groups)
        }

    }

    private fun findSimilarGroup(photos: List<Photo>) {
        lifecycleScope.launch(Dispatchers.IO) {
            val groups = SimilarPhoto.find(requireContext(), photos)
            mainViewModel.setGroups(groups)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    inner class GroupAdapter(private val data: List<Group>) :
        RecyclerView.Adapter<GroupAdapter.ViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup, viewType: Int
        ): ViewHolder {
            return ViewHolder(
                ItemListGroupBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

        override fun onBindViewHolder(
            holder: ViewHolder, position: Int
        ) {
            holder.bind(data[position], position)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        inner class ViewHolder(private val binding: ItemListGroupBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(bean: Group, position: Int) {
                binding.name.text = "Group: $position Size ${bean.photos.size}"
                binding.groupRv.adapter = PhotoAdapter(bean.photos)
            }
        }
    }

    inner class PhotoAdapter(private val data: List<Photo>) :
        RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup, viewType: Int
        ): ViewHolder {
            return ViewHolder(
                ItemGroupImageBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

        override fun onBindViewHolder(
            holder: ViewHolder, position: Int
        ) {
            holder.bind(data[position])
        }

        override fun getItemCount(): Int {
            return data.size
        }

        inner class ViewHolder(private val binding: ItemGroupImageBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(bean: Photo) {
                binding.image.load(bean.path)
                binding.imageName.text = bean.path?.replace("storage/emulated/0", "SD")
                binding.imageSize.text = (bean.size / 1024).toString() + "kb"
            }
        }
    }
}
